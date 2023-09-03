package com.helmes.helmes_test.domain.sector.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.helmes.helmes_test.domain.sector.model.Sector;
import com.helmes.helmes_test.domain.sector.repository.SectorRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SectorServiceTest {
    private static final String PREFIX = "    ";
    private static final String TEST_NAME = "testName";

    @Mock
    private SectorRepository sectorRepository;
    @InjectMocks
    private SectorService sectorService;

    @Test
    void getAllFlattenedWithIndentation() {
        var parentSectors = List.of(
                makeSector(),
                makeSector(),
                makeSectorWithChildren(List.of(makeSector(), makeSectorWithChildren(List.of(makeSector()))))
        );
        when(sectorRepository.findAllByParent(null)).thenReturn(parentSectors);

        var result = sectorService.getAllFlattenedWithIndentation();

        assertThat(parentSectors.size()).isEqualTo(3);
        assertThat(result.size()).isEqualTo(6);
        assertThat(result.get(3).getName()).isEqualTo(result.get(4).getName()).isEqualTo(PREFIX + TEST_NAME);
        assertThat(result.get(5).getName()).isEqualTo(PREFIX.repeat(2) + TEST_NAME);
    }

    private Sector makeSector() {
        return makeSectorWithChildren(List.of());
    }

    private Sector makeSectorWithChildren(List<Sector> childSectors) {
        var sector = new Sector();
        sector.setName(TEST_NAME);
        sector.setChildren(childSectors);

        return sector;
    }
}
