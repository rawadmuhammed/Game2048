import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pis.Game.Model.Model;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    Model m;
    @BeforeEach
    void setup(){
        m = new Model();
    }
    @AfterEach
    void tearDown(){
    }

    @Test
    void atBeginningFourteenSlutsAreEmpty() {
        assertEquals(14, m.free_slots(m.grid));
    }

    @Test
    void onGameStartFreeslutsEqualsFourteen() {
        Model m1 = new Model();
        assertEquals(14, m1.free_slots(m1.grid));
    }

    @Test
    void backToOriginalAfterRoutatingTheField() {
        int [] grid2 = m.grid.clone();
        m.rotate(m.grid, 2);
        m.rotate(m.grid, 2);
        assertArrayEquals(m.grid, grid2);
    }

    @Test
    void afterInsertingRandomTileFieldHasOneMoreTiles() {

        assertEquals(14, m.free_slots(m.grid));
        m.random_tile(m.grid);
        assertEquals(13, m.free_slots(m.grid));
    }

    @Test
    void ifFieldIsNotFullGameOverIsFalse(){
        assertTrue((m.free_slots(m.grid)==0) && (m.is_game_over(m.grid)));
    }
}