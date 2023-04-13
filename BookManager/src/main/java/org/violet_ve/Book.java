package org.violet_ve;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        return getISBN().equals(book.getISBN());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("《").append(Name).append('》');
        sb.append("\tISBN：").append(ISBN);
        sb.append("\t价格：").append(Price).append("￥");
        sb.append("\t分类：").append(Categories);
        sb.append("\t作者：").append(Authors);
        sb.append("\t页数：").append(Pages);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return getISBN().hashCode();
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public List<String> getCategories() {
        return Categories;
    }

    public void setCategories(List<String> categories) {
        Categories = categories;
    }

    public List<String> getAuthors() {
        return Authors;
    }

    public void setAuthors(List<String> authors) {
        Authors = authors;
    }

    public Integer getPages() {
        return Pages;
    }

    public void setPages(Integer pages) {
        Pages = pages;
    }

    private String Name;
    private String ISBN;
    private Double Price;
    private List<String> Categories;
    private List<String> Authors;
    private Integer Pages;

    private Book(String name, String ISBN, Double price, List<String> categories, List<String> authors, Integer pages) {
        Name = name;
        this.ISBN = ISBN;
        Price = price;
        Categories = categories;
        Authors = authors;
        Pages = pages;
    }

    public static BookBuilder builder() {
        return new BookBuilder();
    }

    public static class BookBuilder {
        private String Name;
        private String ISBN;
        private Double Price;
        private List<String> Categories;
        private List<String> Authors;
        private Integer Pages;

        private BookBuilder() {
        }

        public BookBuilder Name(String Name) {
            this.Name = Name;
            return this;
        }

        public BookBuilder ISBN(String ISBN) {
            this.ISBN = ISBN;
            return this;
        }

        public BookBuilder Price(Double Price) {
            this.Price = Price;
            return this;
        }

        public BookBuilder Categories(List<String> Categories) {
            this.Categories = Categories;
            return this;
        }

        public BookBuilder Authors(List<String> Authors) {
            this.Authors = Authors;
            return this;
        }

        public BookBuilder Pages(Integer Pages) {
            this.Pages = Pages;
            return this;
        }

        public Book build() {
            return new Book(Name, ISBN, Price, Categories, Authors, Pages);
        }
    }
}
