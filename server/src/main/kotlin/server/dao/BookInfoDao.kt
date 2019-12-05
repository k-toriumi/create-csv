package server.dao

import io.micronaut.core.annotation.Introspected
import org.seasar.doma.Dao
import org.seasar.doma.Select
import server.AppConfig
import server.entity.BookInfoEntity
import java.util.stream.Stream

@Dao(config = AppConfig::class)
@Introspected
interface BookInfoDao {
    @Select
    fun selectAll(): Stream<BookInfoEntity>
}