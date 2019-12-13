package server.service

import io.micronaut.runtime.http.scope.RequestScope
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import server.dao.BookInfoDaoImpl
import java.io.OutputStreamWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.util.function.Supplier

@RequestScope
class Service {

    val CSV_COLUMNS = listOf("書籍ID", "書籍名", "ページ数",
            "出版社", "発売日", "ISBN", "備考", "著者ID", "著者名", "詳細", "書籍更新日時", "著者更新日時")
    val BOM_BYTES = arrayOf(0xef, 0xbb, 0xbf)

    fun download(): PipedInputStream {

        var pipedOutputStream = PipedOutputStream()
        var pipedInputStream = PipedInputStream(pipedOutputStream)
        var tm = AppConfig.transactionManager

        Thread {
            // BOMを付与
            BOM_BYTES.forEach { pipedOutputStream.write(it) }
            val writer = OutputStreamWriter(pipedOutputStream, Charsets.UTF_8)
            CSVPrinter(writer, CSVFormat.DEFAULT).use { printer ->
                printer.printRecord(CSV_COLUMNS)


                tm.required(Supplier {
                    var dao = BookInfoDaoImpl()
                    var result = dao.selectAll()
                    result.forEach {
                        //                        println(it.book_id)
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
            }
            pipedOutputStream.close()
        }.start()
        return pipedInputStream
    }
}