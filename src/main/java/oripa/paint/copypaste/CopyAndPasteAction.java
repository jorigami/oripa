package oripa.paint.copypaste;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D.Double;

import javax.vecmath.Vector2d;

import oripa.ORIPA;
import oripa.paint.EditMode;
import oripa.paint.GraphicMouseActionInterface;
import oripa.paint.PaintContextInterface;
import oripa.paint.core.GraphicMouseAction;

public class CopyAndPasteAction extends GraphicMouseAction {

	private ChangeOriginAction originAction = new ChangeOriginAction();
	private PasteAction pasteAction = new PasteAction();
	
	private GraphicMouseActionInterface action = pasteAction;

	
	public CopyAndPasteAction() {
		setEditMode(EditMode.COPY);
		setNeedSelect(true);
	}

	private OriginHolder originHolder = OriginHolder.getInstance();

	@Override
	public void recover(PaintContextInterface context) {
		originHolder.resetOrigin(context);
		action = pasteAction;
		action.recover(context);
	}
	
	
	
	@Override
	public void destroy(PaintContextInterface context) {
		originHolder.setOrigin(null);
		action.destroy(context);
	}

	@Override
	public void undo(PaintContextInterface context) {
		ORIPA.doc.loadUndoInfo();
	}
	
	
	@Override
	public GraphicMouseActionInterface onLeftClick(PaintContextInterface context,
			AffineTransform affine, boolean differentAction) {
		action.onLeftClick(context, affine, differentAction);
		
		return this;
	}
	
	
	
//	@Override
//	public void onRightClick(PaintContext context, AffineTransform affine,
//			boolean differentAction) {
//		// TODO Auto-generated method stub
//		super.onRightClick(context, affine, differentAction);
//	}

	@Override
	public void doAction(PaintContextInterface context, Double point,
			boolean differntAction) {
		action.doAction(context, point, differntAction);
	}
	
	
	@Override
	public void onPress(PaintContextInterface context, AffineTransform affine,
			boolean differentAction) {
		action.onPress(context, affine, differentAction);
	}

	@Override
	public void onDrag(PaintContextInterface context, AffineTransform affine,
			boolean differentAction) {
		action.onDrag(context, affine, differentAction);
	}

	@Override
	public void onRelease(PaintContextInterface context, AffineTransform affine,
			boolean differentAction) {
		action.onRelease(context, affine, differentAction);
	}
	
	/**
	 * 
	 * @param changingOrigin {@code true} for selecting origin, {@code false} for pasting.
	 */
	public void changeAction(boolean changingOrigin){
		if(changingOrigin){
			action = originAction;
		}
		else {
			action = pasteAction;
		}
	}
	
	@Override
	public Vector2d onMove(PaintContextInterface context, AffineTransform affine,
			boolean changingOrigin) {
		
		changeAction(changingOrigin);
		
		return action.onMove(context, affine, changingOrigin);
	}

	@Override
	public void onDraw(Graphics2D g2d, PaintContextInterface context) {
		// TODO Auto-generated method stub
		action.onDraw(g2d, context);
	}
}
