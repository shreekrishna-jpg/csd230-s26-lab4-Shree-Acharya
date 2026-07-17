package app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PEN")
public class PenEntity extends StationeryEntity {

    @Column(name = "ink_color")
    private String inkColor;

    @Column(name = "tip_size")
    private Double tipSize;

    public PenEntity() {}

    public PenEntity(String brand, boolean isEcoFriendly, String inkColor, Double tipSize) {
        this.setBrand(brand);
        this.setIsEcoFriendly(isEcoFriendly);
        this.inkColor = inkColor;
        this.tipSize = tipSize;
    }

    public String getInkColor() { return inkColor; }
    public void setInkColor(String inkColor) { this.inkColor = inkColor; }

    public Double getTipSize() { return tipSize; }
    public void setTipSize(Double tipSize) { this.tipSize = tipSize; }

    @Override
    public void sellItem() {
        System.out.println("Selling Pen: " + getBrand() + " (" + inkColor + ")");
    }

    @Override
    public Double getPrice() { return 0.0; }
}