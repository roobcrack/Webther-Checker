package com.ruben.WebtherChecker.Entities;

import java.util.List;

public class NewsResponse {
    private List<Article> articles;

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
}
