package org.violet_ve;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    private static final List<Book> Books = Load();

    public static void main(String[] args) {
        Scanner menuScanner = new Scanner(new BufferedInputStream(System.in));
        try {
            while (true) {
                System.out.println("1. 书籍入库");
                System.out.println("2. 查看所有书籍信息");
                System.out.println("3. 修改书籍信息");
                System.out.println("4. 删除书籍");
                System.out.println("5. 退出系统");
                int menu = menuScanner.nextInt();
                switch (menu) {
                    case 5:
                        Exit();
                        break;
                    case 4:
                        Delete();
                        break;
                    case 3:
                        Modify();
                        break;
                    case 2:
                        Query();
                        break;
                    case 1:
                        Create();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Exit() {
        Save();
        System.out.println("感谢您的使用，再见！");
        System.exit(0);
    }

    private static void Create() {
        try {
            System.out.println("==请输入要入库的书籍信息==");
            System.out.print("名称：");
            Scanner bookScanner = new Scanner(new BufferedInputStream(System.in));
            String bookName = bookScanner.nextLine();
            System.out.print("ISBN：");
            String bookISBN = bookScanner.nextLine();
            System.out.print("价格：");
            Double bookPrice = bookScanner.nextDouble();
            bookScanner.nextLine();
            System.out.print("分类(,分隔)：");
            String categoriesStr = bookScanner.nextLine();
            System.out.print("作者(,分隔)：");
            String authorsStr = bookScanner.nextLine();
            System.out.print("页数：");
            Integer bookPages = bookScanner.nextInt();
            List<String> categories = List.of(categoriesStr.split(","));
            List<String> authors = List.of(authorsStr.split(","));
            Book book = Book.builder()
                    .Name(bookName)
                    .ISBN(bookISBN)
                    .Price(bookPrice)
                    .Categories(categories)
                    .Authors(authors)
                    .Pages(bookPages)
                    .build();
            Books.add(book);
            System.out.print("入库完成！书籍信息：");
            System.out.println(book);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Delete() {
        try {
            Scanner scanner = new Scanner(new BufferedInputStream(System.in));
            while (true) {
                System.out.println("目前已有书籍：");
                Query();
                System.out.println("请输入需要删除的书籍编号(0返回菜单)：");
                int menu = scanner.nextInt();
                scanner.nextLine();
                if (menu > Books.size() || menu < 0) {
                    System.out.println("编号输入错误！");
                }
                switch (menu) {
                    case 0:
                        return;
                    default:
                        Books.remove(menu - 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Modify() {
        try {
            Scanner scanner = new Scanner(new BufferedInputStream(System.in));
            while (true) {
                System.out.println("目前已有书籍：");
                Query();
                System.out.println("请输入需要修改的书籍编号(0返回菜单)：");
                int menu = scanner.nextInt();
                scanner.nextLine();
                if (menu > Books.size() || menu < 0) {
                    System.out.println("编号输入错误！");
                }
                switch (menu) {
                    case 0:
                        return;
                    default: {
                        System.out.println("可以修改的内容为：\t作者\t名称\tISBN\t价格\t分类\t页数");
                        System.out.println("请输入需要修改的内容(输入其他内容返回上级)：");
                        String key = scanner.nextLine();
                        menu--;
                        switch (key) {
                            case "作者":
                                System.out.print("请输入新作者(,分隔)：");
                                String authorsStr = scanner.nextLine();
                                List<String> authors = List.of(authorsStr.split(","));
                                Books.get(menu).setAuthors(authors);
                                break;
                            case "名称":
                                System.out.print("请输入名称：");
                                String Name = scanner.nextLine();
                                Books.get(menu).setName(Name);
                                break;
                            case "ISBN":
                                System.out.print("请输入新ISBN：");
                                String ISBN = scanner.nextLine();
                                Books.get(menu).setISBN(ISBN);
                                break;
                            case "价格":
                                System.out.print("请输入新价格：");
                                Double Price = scanner.nextDouble();
                                scanner.nextLine();
                                Books.get(menu).setPrice(Price);
                                break;
                            case "分类":
                                System.out.print("请输入新分类(,分隔)：");
                                String categoriesStr = scanner.nextLine();
                                List<String> categories = List.of(categoriesStr.split(","));
                                Books.get(menu).setCategories(categories);
                                break;
                            case "页数":
                                System.out.print("请输入新页数：");
                                Integer Pages = scanner.nextInt();
                                scanner.nextLine();
                                Books.get(menu).setPages(Pages);
                                break;
                            default:
                                System.out.println("输入错误！");
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Query() {
        try {
            if (Books.size() == 0) System.out.println("暂时没有书籍哦~");
            else System.out.println("所有书籍信息:");
            for (int i = 0; i < Books.size(); i++) {
                System.out.println((i + 1) + ": " + Books.get(i));
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Save() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get("./book.sav")))) {
            outputStream.writeObject(Books);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static LinkedList<Book> Load() {
        File file = new File("./book.sav");
        if (!file.exists()) return new LinkedList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(file.toPath()))) {
            return (LinkedList<Book>) inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }
}