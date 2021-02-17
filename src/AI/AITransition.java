package AI;

import entity.MovingEntity;


public class AITransition {
    private String nextState;
    private AICondition condition;

    public AITransition(String nextState, AICondition condition) {
        this.nextState = nextState;
        this.condition = condition;
    }
    public boolean shouldTransition(MovingEntity currentCharacter){
        return condition.isMet(currentCharacter);
    }

    public String getNextState() {
        return nextState;
    }
}
