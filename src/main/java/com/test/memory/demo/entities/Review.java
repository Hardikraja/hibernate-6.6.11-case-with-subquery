package com.test.memory.demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String comment;
    private Integer rating;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public AppUser getAppUser() { return appUser; }
    public void setAppUser(AppUser appUser) { this.appUser = appUser; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
