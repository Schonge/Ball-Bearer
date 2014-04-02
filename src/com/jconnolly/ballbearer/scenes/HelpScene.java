package com.jconnolly.ballbearer.scenes;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;

import com.jconnolly.ballbearer.SceneManager;
import com.jconnolly.ballbearer.SceneManager.SceneType;
import com.jconnolly.ballbearer.resourcemanagers.MenuResourceManager;

public class HelpScene extends BaseScene implements IOnMenuItemClickListener {
	
	private MenuScene menuChild;
	private final int BACK = 0;
	
	private Sprite help;

	@Override
	public void createScene() {
		createHelp();
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.HELP_SCENE;
	}

	@Override
	public void onBackPressed() {
		System.exit(0);
	}

	@Override
	public void destroyScene() {
		help.detachSelf();
		help.dispose();
		this.detachSelf();
		this.dispose();
	}
	
	public void createHelp() {
		// Background
		this.help = createSprite(0, 0, MenuResourceManager.getMenuResMan().helpBackTR, vbom);
		attachChild(this.help);
		
		// Back buttons
		menuChild = new MenuScene(camera);
		menuChild.setPosition(0, 0);
		
		// ScaleMenuItemDecorator provides a scale animation for menu buttons when pressed
		final IMenuItem backItem = new ScaleMenuItemDecorator(new SpriteMenuItem(BACK, MenuResourceManager.getMenuResMan().backBtnTR, vbom), 1.2f, 1);
		menuChild.addMenuItem(backItem);
		
		menuChild.buildAnimations();
		menuChild.setBackgroundEnabled(false);
		backItem.setPosition(backItem.getX(), backItem.getY() + 350);
		
		menuChild.setOnMenuItemClickListener(this);
		setChildScene(menuChild);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		SceneManager.getSceneMan().createMenu();
		destroyScene();
		return true;
	}

}
