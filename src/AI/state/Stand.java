package AI.state;

import AI.AITransition;
import entity.MovingEntity;
import map.SymulatorMap;

public class Stand extends AIState{

    private int updatesAlive; // for how much time we have been standing

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("wander",( currentCharacter -> updatesAlive >= 5));
    }

    /**
     * updates time to check when to transition to wander State
     * @param symulatorMap
     * @param currentCharacter
     */
    @Override
    public void update(SymulatorMap symulatorMap, MovingEntity currentCharacter) {
        updatesAlive++;
    }
}
