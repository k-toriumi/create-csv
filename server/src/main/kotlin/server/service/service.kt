package server.service

import io.micronaut.runtime.http.scope.RequestScope
import server.AppConfig
import server.dao.BookInfoDao
import server.dao.BookInfoDaoImpl
import server.entity.BookInfoEntity
import java.util.function.Supplier
import java.util.stream.Stream
import javax.inject.Inject
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter

@RequestScope
class Service{

    val CSV_COLUMNS = listOf("書籍ID", "書籍名", "ページ数",
            "出版社", "発売日", "ISBN", "備考", "著者ID", "著者名", "詳細", "書籍更新日時", "著者更新日時")
    val BOM_BYTES = arrayOf(0xef, 0xbb, 0xbf)

    fun download(): ByteArray {
        val baos = ByteArrayOutputStream()
        // BOMを付与
        BOM_BYTES.forEach { baos.write(it) }
        val writer = OutputStreamWriter(baos, Charsets.UTF_8)
        CSVPrinter(writer, CSVFormat.DEFAULT).use { printer ->
            printer.printRecord(CSV_COLUMNS)

            var tm = AppConfig.transactionManager
            tm.required(Supplier {
                var dao = BookInfoDaoImpl()
                var result = dao.selectAll()
                result.forEach() {
                    println(it.book_id)
                    printer.printRecord(
                            it.book_id,
                            it.book_name,
                            it.page,
                            it.publisher,
                            it.sale_date,
                            it.isbn,
                            it.book_note,
                            it.author_id,
                            it.author_name,
                            it.author_note,
                            it.book_update_date,
                            it.author_update_date)
                }
            })
            return baos.toByteArray()
        }
    }
}