package com.despegar.highflight;

public class MinesweeperCell implements Cell <Character>{
	Character value;
	boolean flagged;
	boolean covered;
	public MinesweeperCell(){
		this.value='0';
		this.flagged=false;
		this.covered=true;
	}
	public MinesweeperCell(Character value){
		this.value=value;
		this.flagged=false;
		this.covered=true;
	}
	public boolean isFlagged() {
		// TODO Auto-generated method stub
		return this.flagged;
	}

	public Character getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	public void setValue(Character value) {
		this.value=value;
	}

	public void unFlag() {
		this.flagged=false;
	}

	public void flag() {
		this.flagged=true;
	}
	public void unCover(){
		this.covered=false;
	}
	public boolean isCovered(){
		return covered;
	}
	public char display(){
		if(this.flagged)return 'F';
		else if (this.covered) return ' ';
		return this.value;
	}
}
