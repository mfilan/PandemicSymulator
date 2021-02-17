package AI;

import entity.MovingEntity;
public interface AICondition {
    boolean isMet(MovingEntity currentCharacter);

}
