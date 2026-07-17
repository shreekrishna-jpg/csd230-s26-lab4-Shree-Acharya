package app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class StationeryEntity extends ProductEntity {

    @Column(name = "brand")
    private String brand;

    @Column(name = "is_eco_friendly")
    private boolean isEcoFriendly;

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public boolean getIsEcoFriendly() { return isEcoFriendly; }
    public void setIsEcoFriendly(boolean isEcoFriendly) { this.isEcoFriendly = isEcoFriendly; }
}