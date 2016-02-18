package de.desertfox.analyse.whatsapp.model;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum ImageTemplate implements IComboViewableEnum {
    CAT("Katze", "\\res\\cat.png"), 
    CAT_1("Katze 1", "\\res\\cat_1.png"), 
    CAT_2("Katze 2", "\\res\\cat_2.png"), 
    CAT_3("Katze 3", "\\res\\cat_3.png"), 
    CAT_4("Katze 4", "\\res\\cat_4.png"), 
    DOG_1("Hund 1", "\\res\\dog_1.png"),
    DOG_2("Hund 2", "\\res\\dog_2.png"),
    ST_PAULI("FC St. Pauli", "\\res\\st_pauli.png")
    ;

    private String displayName;
    private String path;
    private String id;

    private ImageTemplate(String displayName, String path) {
        this.displayName = displayName;
        this.path = path;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getPath() {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString() + path;
    }

}
