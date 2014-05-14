public abstract class Type
{
	protected final int type;

	public void setType(int type) { this.type = type; }
	public abstract void affect(Card origin, Card target);
	public boolean matches(int effect) { return (type & effect) == effect; }
}