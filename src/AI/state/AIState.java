package AI.state;

import AI.AITransition;
import entity.MovingEntity;
import map.SymulatorMap;

public abstract class AIState {

    private AITransition transition;

    public AIState() {
        this.transition = initializeTransition();
    }

    protected abstract AITransition initializeTransition();
    public abstract void update(SymulatorMap symulatorMap, MovingEntity currentCharacter);

    /**
     * check if it is time to change AI state
     * @param currentCharacter
     */
    public boolean shouldTransition( MovingEntity currentCharacter){
        return transition.shouldTransition(currentCharacter);
    }

    public String getNextState(){
        return transition.getNextState();
    }

}
