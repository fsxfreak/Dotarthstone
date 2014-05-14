public abstract class CardType
{
	public static final int MINION = 3 << 0;
	public static final int SPELL = 3 << 1;
	public static final int TOTEM = 3 << 2;
	public static final int DRAGON = 3 << 3;

	protected int type;

	public void setType(int type) { this.type = type; }
	public boolean matches(int type) { return (this.type & type) == type }
}