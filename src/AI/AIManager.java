package AI;

import AI.state.AIState;
import AI.state.Stand;
import AI.state.Wander;
import entity.MovingEntity;
import map.SymulatorMap;


public class AIManager {

    private AIState currentAIState;
    protected String floorType;

    public AIManager(String floorType) {
        transitionTo("stand");
        this.floorType = floorType;
    }


    public void update(SymulatorMap symulatorMap, MovingEntity currentCharacter){
        currentAIState.update(symulatorMap,currentCharacter);

        if (currentAIState.shouldTransition(currentCharacter)){
            transitionTo(currentAIState.getNextState());
        }

    }

    public void setCurrentAIState(AIState currentAIState) {
        this.currentAIState = currentAIState;
    }

    public AIState getCurrentAIState() {
        return currentAIState;
    }

    public void transitionTo(String nextState) {
        switch(nextState){
            case "wander":
                currentAIState = new Wander(floorType);
                return;
            case "stand":
            default:
                currentAIState = new Stand();
        }
    }

}
