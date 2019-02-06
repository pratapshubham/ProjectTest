package com.example.braintech.projecttest.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeFragmentModel
{

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("data")
        @Expose
        private Data data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    public class Data {

        @SerializedName("filter_data")
        @Expose
        private FilterData filterData;
        @SerializedName("products")
        @Expose
        private List<Product> products = null;

        public FilterData getFilterData() {
            return filterData;
        }

        public void setFilterData(FilterData filterData) {
            this.filterData = filterData;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }
        public class FilterData {

            @SerializedName("category")
            @Expose
            private Integer category;
            @SerializedName("filter")
            @Expose
            private String filter;
            @SerializedName("sort")
            @Expose
            private String sort;
            @SerializedName("order")
            @Expose
            private String order;
            @SerializedName("page")
            @Expose
            private Integer page;
            @SerializedName("limit")
            @Expose
            private String limit;
            @SerializedName("page_title")
            @Expose
            private String pageTitle;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("total_pagination")
            @Expose
            private Integer totalPagination;

            public Integer getCategory() {
                return category;
            }

            public void setCategory(Integer category) {
                this.category = category;
            }

            public String getFilter() {
                return filter;
            }

            public void setFilter(String filter) {
                this.filter = filter;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getOrder() {
                return order;
            }

            public void setOrder(String order) {
                this.order = order;
            }

            public Integer getPage() {
                return page;
            }

            public void setPage(Integer page) {
                this.page = page;
            }

            public String getLimit() {
                return limit;
            }

            public void setLimit(String limit) {
                this.limit = limit;
            }

            public String getPageTitle() {
                return pageTitle;
            }

            public void setPageTitle(String pageTitle) {
                this.pageTitle = pageTitle;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Integer getTotalPagination() {
                return totalPagination;
            }

            public void setTotalPagination(Integer totalPagination) {
                this.totalPagination = totalPagination;
            }

        }

        public class Product {

            @SerializedName("product_id")
            @Expose
            private String productId;
            @SerializedName("thumb")
            @Expose
            private String thumb;
            @SerializedName("additional_image")
            @Expose
            private List<Object> additionalImage = null;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("model")
            @Expose
            private String model;
            @SerializedName("paconumber")
            @Expose
            private String paconumber;
            @SerializedName("fits")
            @Expose
            private String fits;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("special")
            @Expose
            private Boolean special;
            @SerializedName("tax")
            @Expose
            private String tax;
            @SerializedName("minimum")
            @Expose
            private String minimum;
            @SerializedName("rating")
            @Expose
            private Integer rating;
            @SerializedName("href")
            @Expose
            private String href;

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public List<Object> getAdditionalImage() {
                return additionalImage;
            }

            public void setAdditionalImage(List<Object> additionalImage) {
                this.additionalImage = additionalImage;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getPaconumber() {
                return paconumber;
            }

            public void setPaconumber(String paconumber) {
                this.paconumber = paconumber;
            }

            public String getFits() {
                return fits;
            }

            public void setFits(String fits) {
                this.fits = fits;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public Boolean getSpecial() {
                return special;
            }

            public void setSpecial(Boolean special) {
                this.special = special;
            }

            public String getTax() {
                return tax;
            }

            public void setTax(String tax) {
                this.tax = tax;
            }

            public String getMinimum() {
                return minimum;
            }

            public void setMinimum(String minimum) {
                this.minimum = minimum;
            }

            public Integer getRating() {
                return rating;
            }

            public void setRating(Integer rating) {
                this.rating = rating;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

        }

    }

    }


