package com.micropos.products.repository;

import com.micropos.products.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JDRepository implements ProductRepository {

    private volatile List<Product> products = null;

    @Override
    public List<Product> allProducts() {
        if (products == null) {
            synchronized (this) {
                if (products == null) {
                    try {
                        products = parseJD("java");
                        products = products.stream().filter(p -> !p.getId().isBlank()).collect(Collectors.toList());
                    } catch (IOException e) {
                        products = null;
                        e.printStackTrace();
                    }
                }
            }
        }
        return products;
    }

    @Override
    public Product findProduct(String productId) {
        for (Product p : allProducts()) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public int productsCount() {
        List<Product> products = allProducts();
        return products == null ? 0 : products.size();
    }

    @Override
    public List<Product> productsInRange(int fromIndex, int count) {
        List<Product> products = allProducts();
        if (products == null || fromIndex < 0 || fromIndex >= products.size()) {
            return new ArrayList<>();
        }
        int toIndex = Math.min(fromIndex + count, products.size());
        return products.subList(fromIndex, toIndex);
    }

    public static List<Product> parseJD(String keyword) throws IOException {
        String url = "https://search.jd.com/Search?keyword=" + keyword;
        Document document = Jsoup.parse(new URL(url), 10000);
        Element element = document.getElementById("J_goodsList");
        if (element == null) {
            return null;
        }
        Elements elements = element.getElementsByTag("li");

        List<Product> list = new ArrayList<>();
        for (Element el : elements) {
            String id = el.attr("data-spu");
            String img = "https:".concat(el.getElementsByTag("img").eq(0).attr("data-lazy-img"));
            String price = el.getElementsByAttribute("data-price").text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            if (title.contains("，")) {
                title = title.substring(0, title.indexOf("，"));
            }
            Product product = new Product(id, title, Double.parseDouble(price), img);
            list.add(product);
        }

        return list;
    }
}
