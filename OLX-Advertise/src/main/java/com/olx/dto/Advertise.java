package com.olx.dto;

import java.sql.Blob;
import java.time.LocalDate;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Advertise Model")
public class Advertise {

	@ApiModelProperty("unidue id for the advertise")
	private int id;
	
	@ApiModelProperty("title for the advertise")
	private String title;
	
	@ApiModelProperty("price for the advertise")
	private int price;
	
	@ApiModelProperty("category id for the advertise")
	private int categoryId;
	
	@ApiModelProperty("description for the advertise")
	private String description;
	
	@ApiModelProperty("username for the advertise")
	private String userName;
	
	@ApiModelProperty("created date of the advertise")
	private LocalDate createdDate;
	
	@ApiModelProperty("modified date of the advertisee")
	private LocalDate modifiedDate;
	
	@ApiModelProperty("status of the advertise")
	private String status;

	@ApiModelProperty("photo of the advertise")
	private Blob photo;

	public Blob getPhoto() {
		return photo;
	}
	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
	
	public Advertise(int id, String title, int price, int categoryId, String description, String userName,
			LocalDate createdDate, LocalDate modifiedDate, String status, Blob photo) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.categoryId = categoryId;
		this.description = description;
		this.userName = userName;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.status = status;
		this.photo = photo;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public Advertise() {
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
