package server.entity

import org.seasar.doma.*

@Entity(immutable = true)
@Table(name = "book_info", schema = "postgres")
data class BookInfoEntity(

        /**
         * 書籍ID
         */
        @Column(name = "book_id")
        val book_id: Int,

        /**
         * 書籍名
         */
        @Column(name = "book_name")
        val book_name: String,

        /**
         * ページ数
         */
        @Column(name = "page")
        val page: String,

        /**
         * 出版社
         */
        @Column(name = "publisher")
        val publisher: String,

        /**
         * 発売日
         */
        @Column(name = "sale_date")
        val sale_date: String,

        /**
         * ISBN
         */
        @Column(name = "isbn")
        val isbn: String,

        /**
         * 備考
         */
        @Column(name = "book_note")
        val book_note: String,

        /**
         * 著者ID
         */
        @Column(name = "author_id")
        val author_id: String,

        /**
         * 著者名
         */
        @Column(name = "author_name")
        val author_name: String,

        /**
         * 備考
         */
        @Column(name = "author_note")
        val author_note: String,

        /**
         * 書籍最終更新日時
         */
        @Column(name = "book_update_date")
        val book_update_date: String,

        /**
         * 著者最終更新日時
         */
        @Column(name = "author_update_date")
        val author_update_date: String
)