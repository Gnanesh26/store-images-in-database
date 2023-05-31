package file.upload.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "FileData")
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Lob
    @Column(name = "imagedata")
    private byte[] imageData;


    public FileData() {
    }

    private FileData(String name, String type, byte[] imageData) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public static class Builder {
        private String name;
        private String type;
        private byte[] imageData;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder imageData(byte[] imageData) {
            this.imageData = imageData;
            return this;
        }

        public FileData build() {
            return new FileData(name, type, imageData);
        }


    }

    public static Builder builder() {
        return new Builder();
    }
}



