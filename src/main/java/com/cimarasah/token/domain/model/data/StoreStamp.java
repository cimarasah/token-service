package com.cimarasah.token.domain.model.data;

import org.hibernate.validator.constraints.NotBlank;

import com.cimarasah.token.domain.model.TokenData;

import javax.validation.constraints.NotNull;

public class StoreStamp implements TokenData {

    @NotNull(message = "Id do Usuário não pode ser vazio.")
    private Long userId;

    @NotBlank(message = "Usuário não pode ser vazio.")
    private String username;

    @NotNull(message = "Código da empresa obrigatório.")
    private Long company;

    @NotNull(message = "Código da TAB obrigatório.")
    private Long storeId;

    @NotNull(message = "Código da loja obrigatório.")
    private Long branchId;

    private Long regionalId;

    private Long regionId;

    @NotNull(message = "Código do departamento obrigatório.")
    private Long departmentId;

    @NotNull(message = "Código do canal de origem obrigatório.")
    private String sourceChannelCode;

    @NotNull(message = "Código do canal obrigatório.")
    private Long sourceChannelId;

    @NotBlank(message = "Código do produto não pode ser vazio.")
    private String productId;

    @NotNull(message = "Código da organização não pode ser vazio.")
    private Long organizationId;

    private Boolean onlineLoan;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCompany() {
        return company;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getRegionalId() {
        return regionalId;
    }

    public void setRegionalId(Long regionalId) {
        this.regionalId = regionalId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getSourceChannelCode() {
        return sourceChannelCode;
    }

    public void setSourceChannelCode(String sourceChannelCode) {
        this.sourceChannelCode = sourceChannelCode;
    }

    public Long getSourceChannelId() {
        return sourceChannelId;
    }

    public void setSourceChannelId(Long sourceChannelId) {
        this.sourceChannelId = sourceChannelId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Boolean getOnlineLoan() {
        return onlineLoan;
    }

    public void setOnlineLoan(Boolean onlineLoan) {
        this.onlineLoan = onlineLoan;
    }
}
