package com.helmes.helmes_test.domain.sector.service;

import com.helmes.helmes_test.domain.sector.model.Sector;
import com.helmes.helmes_test.domain.sector.repository.SectorRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectorService {

    private static final String PREFIX = "    ";
    private final SectorRepository sectorRepository;

    public List<Sector> getAllFlattenedWithIndentation() {
        return addIndentationAndGetFlattened(getTopLevelSectors());
    }

    private List<Sector> getTopLevelSectors() {
        return sectorRepository.findAllByParent(null);
    }

    private List<Sector> addIndentationAndGetFlattened(List<Sector> topLevelSectors) {
        topLevelSectors.forEach(sector -> addIndentation(sector, 0));

        return flatten(topLevelSectors);
    }

    private void addIndentation(Sector sector, int depth) {
        var prefix = new StringBuilder();

        prefix.append(PREFIX.repeat(depth));
        sector.setName(prefix.append(sector.getName()).toString());
        sector.getChildren().forEach(child -> addIndentation(child, depth + 1));
    }

    private List<Sector> flatten(List<Sector> sectors) {
        var flattened = new ArrayList<Sector>();

        sectors.forEach(sector -> {
            flattened.add(sector);
            flattened.addAll(flatten(sector.getChildren()));
        });

        return flattened;
    }
}
