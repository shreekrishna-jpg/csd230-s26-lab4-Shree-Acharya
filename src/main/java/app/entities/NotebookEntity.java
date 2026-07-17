package app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NOTEBOOK")
public class NotebookEntity extends StationeryEntity {

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "paper_ruling")
    private String paperRuling;

    public NotebookEntity() {}

    public NotebookEntity(String brand, boolean isEcoFriendly, Integer pageCount, String paperRuling) {
        this.setBrand(brand);
        this.setIsEcoFriendly(isEcoFriendly);
        this.pageCount = pageCount;
        this.paperRuling = paperRuling;
    }

    public Integer getPageCount() { return pageCount; }
    public void setPageCount(Integer pageCount) { this.pageCount = pageCount; }

    public String getPaperRuling() { return paperRuling; }
    public void setPaperRuling(String paperRuling) { this.paperRuling = paperRuling; }

    @Override
    public void sellItem() {
        System.out.println("Selling Notebook: " + getBrand() + " (" + paperRuling + ")");
    }

    @Override
    public Double getPrice() { return 0.0; }
}