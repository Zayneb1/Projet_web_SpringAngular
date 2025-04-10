package com.codezayneb.ecom.entity;
import com.codezayneb.ecom.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Long price;
    @Lob
    private String description;


    @Lob
    @Column(columnDefinition ="LONGBLOB")
    private byte[] img;

    //we want many product in one category
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    public ProductDto getDto(){
    ProductDto productDto = new ProductDto();
    productDto.setId(id);
    productDto.setName(name) ;
    productDto.setPrice(price) ;
    productDto.setDescription(description) ;
    productDto.setByteImg(img) ;
    productDto.setCategoryId(category.getId()) ;
    productDto.setCategoryName(category.getName());
    return productDto;

}}
