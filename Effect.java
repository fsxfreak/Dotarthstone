
/*
	Card being operated on should be checked for effects. If effectType is pertinent to the situation,
	should call affect() on card that has the effect.
*/
public abstract class Effect
{
	public static final int ON_ATTACK  = 1 << 0;
	public static final int ON_SUMMON  = 1 << 1;
	public static final int ON_DAMAGED = 1 << 2;
	public static final int ON_HEALED  = 1 << 3; 

	protected int effectType;

	public abstract void affect(Card card);
	public boolean matches(int effect) { return (effectType & effect) == effect; }
}