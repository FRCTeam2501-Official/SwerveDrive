// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class drive extends SubsystemBase {
  /** Creates a new drive. */
  //motors defind   turn is the stering motors and drive is the drive motors
private final CANSparkMax m_turn1 = new CANSparkMax(1, MotorType.kBrushless);   //  *******front*********
private final CANSparkMax m_turn2 = new CANSparkMax(2, MotorType.kBrushless);   //  ********************* 
private final CANSparkMax m_turn3 = new CANSparkMax(3, MotorType.kBrushless);   //  *********************
private final CANSparkMax m_turn4 = new CANSparkMax(4, MotorType.kBrushless);   //  *******swerve********
private final CANSparkMax m_drive1 = new CANSparkMax(5, MotorType.kBrushless);  //  ********drive********
private final CANSparkMax m_drive2 = new CANSparkMax(6, MotorType.kBrushless);  //  *********************
private final CANSparkMax m_drive3 = new CANSparkMax(7, MotorType.kBrushless);  //  *********************
private final CANSparkMax m_drive4 = new CANSparkMax(8, MotorType.kBrushless);  //  *********************  

//drive control groups 
private final MotorControllerGroup m_onefour = new MotorControllerGroup(m_drive1, m_drive4);
private final MotorControllerGroup m_twothree = new MotorControllerGroup(m_drive2, m_drive3);


private final WPI_Pigeon2 Gyro = new WPI_Pigeon2(0);

private final CANCoder number1 = new CANCoder(2);
private final CANCoder number2 = new CANCoder(1);
private final CANCoder number3 = new CANCoder(4);
private final CANCoder number4 = new CANCoder(3);




private double predirection=0;
private double predirection2=0;
private double speedmod;
private double rotations;
private double speedmod2;
private double rotations2;
private double directiono;
private double directiono2;
private double direction2;
private double displacement;
private double displacement2;
private double steardrect1;
private double steardrect2;
private double steardrect3;
private double steardrect4;
private double name;
private double direction;
private double work;


private double gyrospin=0;

private double gyro360;

  public drive() {


  }


//returns encoder valuse
public double number1(){
  return number1.getPosition();
}
public double number2(){
  return number2.getPosition();
}
public double number3(){
  return number3.getPosition();
}
public double number4(){
  return number4.getPosition();
}

//raw gyro value
public double gyro(){
  return Gyro.getYaw();
}

//gyro value from -180 to 180
public double gyro180(){
  
  gyro360 = -Gyro.getYaw();
if(gyro360-360*gyrospin>180){gyrospin=gyrospin+1;}
else if(gyro360-360*gyrospin<-180){gyrospin=gyrospin-1;}
  return gyro360-360*gyrospin;
}

public void zero(){
  Gyro.reset();
}

public void swerve(double irection, double speed, double turn,double mod2){
  
  speedmod=speedmod*mod2;
  //speedmod2=speedmod2*mod2;

  name=irection;
  if(name!=222){
    directiono=irection;
  directiono2=irection+gyro180();
  direction=irection+gyro180();
}

  turn=35*turn;


  if(name==222&&(turn>10||turn<-10)){
direction=-45;
directiono2=45;
  }

// algarithom figers out which dercshon is fastest to get to posishon. motors that are diaganal to echother share an agarithon.
if((name==222&&(turn>10||turn<-10))||name!=222){
  displacement =Math.abs(direction-predirection);
  if(direction<=0){direction2=direction+180;}
  if(direction>0){direction2=direction-180;}
  
  displacement2=Math.abs(direction2-predirection);
  
  if(displacement>270){
    if(direction<=0){direction=direction+360;}
  else{direction=direction-360;}
  displacement = Math.abs(direction-predirection);
  }
  
  if(displacement<=displacement2){
    predirection=direction;
    speedmod=1*speedmod;
  }
  else{
    predirection=direction2;
    speedmod=-1*speedmod;
  }
  
  if(predirection>180){
  predirection=predirection-360;
  rotations=rotations+1;
  }
  else if(predirection<-180){
  predirection=predirection+360;
  rotations=rotations-1;
  }
  //part 2
  direction=directiono2;
  displacement =Math.abs(direction-predirection2);
  if(direction<=0){direction2=direction+180;}
  if(direction>0){direction2=direction-180;}
  
  displacement2=Math.abs(direction2-predirection2);
  
  if(displacement>270){
    if(direction<=0){direction=direction+360;}
  else{direction=direction-360;}
  displacement = Math.abs(direction-predirection2);
  }
  
  if(displacement<=displacement2){
    predirection2=direction;
    speedmod2=1;
  }
  else{
    predirection2=direction2;
    speedmod2=-1;
  }
  
  if(predirection2>180){
  predirection2=predirection2-360;
  rotations2=rotations2+1;
  }
  else if(predirection2<-180){
  predirection2=predirection2+360;
  rotations2=rotations2-1;
  }
}
work=directiono+gyro180();

  if(work>180){work=work-360;}
if(work<-180){work=work+360;}


if(name!=222){
 

if(work<=45&&work>=-45){//0
  steardrect1=predirection2+360*rotations2+turn;
  steardrect2=predirection+360*rotations+turn;
  steardrect3=predirection+360*rotations-turn;
  steardrect4=predirection2+360*rotations2-turn;
}
if(work<=135&&work>45){//90
  steardrect1=predirection2+360*rotations2-turn;
  steardrect2=predirection+360*rotations+turn;
  steardrect3=predirection+360*rotations-turn;
  steardrect4=predirection2+360*rotations2+turn;
}
if(work<-45&&work>=-135){//-90
  steardrect1=predirection2+360*rotations2+turn;
  steardrect2=predirection+360*rotations-turn;
  steardrect3=predirection+360*rotations+turn;
  steardrect4=predirection2+360*rotations2-turn;
}
if(work<-135||work>135){//180
  steardrect1=predirection2+360*rotations2-turn;
  steardrect2=predirection+360*rotations-turn;
  steardrect3=predirection+360*rotations+turn;
  steardrect4=predirection2+360*rotations2+turn;
}

      }

if(name==222&&(turn>10||turn<-10)){

speed=-turn/45;
steardrect3=predirection+360*rotations;
steardrect4=predirection2+360*rotations2;
steardrect2=predirection+360*rotations;
steardrect1=predirection2+360*rotations2;

m_drive1.set(-speed*speedmod2);
m_drive2.set(speed*speedmod);
m_drive3.set(-speed*speedmod);
m_drive4.set(speed*speedmod2);
}

if(name==222&&(turn<10&&turn>-10)){
  m_twothree.set(0);
  m_onefour.set(0);
}



if(name!=222){m_onefour.set(speed*speedmod2);     m_twothree.set(speed*speedmod);}//motor speed

//code for controling posishon of stering motors PID whould be better

if(number1.getPosition()+1<steardrect1&&steardrect1<number1.getPosition()+2){m_turn1.set(.00);}//right speed one
else if(number1.getPosition()+2<=steardrect1&&steardrect1<number1.getPosition()+10){m_turn1.set(.02);}
else if(number1.getPosition()+10<=steardrect1){m_turn1.set(.2);}
else if(number1.getPosition()-1>steardrect1&&steardrect1>number1.getPosition()-2){m_turn1.set(-.00);}
else if(number1.getPosition()-2>=-steardrect1&&steardrect1>number1.getPosition()-10){m_turn1.set(-.02);}
else if(number1.getPosition()-10>=steardrect1){m_turn1.set(-.2);}
else if(number1.getPosition()+1>=steardrect1&&number1.getPosition()-1<=steardrect1){m_turn1.set(0);}




if(number2.getPosition()+1<steardrect2&&steardrect2<number2.getPosition()+2){m_turn2.set(.00);}//right speed one
else if(number2.getPosition()+2<=steardrect2&&steardrect2<number2.getPosition()+10){m_turn2.set(.02);}
else if(number2.getPosition()+10<=steardrect2){m_turn2.set(.2);}
else if(number2.getPosition()-1>steardrect2&&steardrect2>number2.getPosition()-2){m_turn2.set(-.00);}
else if(number2.getPosition()-2>=-steardrect2&&steardrect2>number2.getPosition()-10){m_turn2.set(-.02);}
else if(number2.getPosition()-10>=steardrect2){m_turn2.set(-.2);}
else if(number2.getPosition()+1>=steardrect2&&number2.getPosition()-1<=steardrect2){m_turn2.set(0);}


if(number3.getPosition()+1<steardrect3&&steardrect3<number3.getPosition()+2){m_turn3.set(.00);}//right speed one
else if(number3.getPosition()+2<=steardrect3&&steardrect3<number3.getPosition()+10){m_turn3.set(.02);}
else if(number3.getPosition()+10<=steardrect3){m_turn3.set(.2);}
else if(number3.getPosition()-1>steardrect3&&steardrect3>number3.getPosition()-2){m_turn3.set(-.00);}
else if(number3.getPosition()-2>=-steardrect3&&steardrect3>number3.getPosition()-10){m_turn3.set(-.02);}
else if(number3.getPosition()-10>=steardrect3){m_turn3.set(-.2);}
else if(number3.getPosition()+1>=steardrect3&&number3.getPosition()-1<=steardrect3){m_turn3.set(0);}


if(number4.getPosition()+1<steardrect4&&steardrect4<number4.getPosition()+2){m_turn4.set(.00);}//right speed one
else if(number4.getPosition()+2<=steardrect4&&steardrect4<number4.getPosition()+10){m_turn4.set(.02);}
else if(number4.getPosition()+10<=steardrect4){m_turn4.set(.2);}
else if(number4.getPosition()-1>steardrect4&&steardrect4>number4.getPosition()-2){m_turn4.set(-.00);}
else if(number4.getPosition()-2>=-steardrect4&&steardrect4>number4.getPosition()-10){m_turn4.set(-.02);}
else if(number4.getPosition()-10>=steardrect4){m_turn4.set(-.2);}
else if(number4.getPosition()+1>=steardrect4&&number4.getPosition()-1<=steardrect4){m_turn4.set(0);}

//outputs data for debuging. add // infront of paragrafe comment to enable
///*
SmartDashboard.putNumber("pred", predirection);
SmartDashboard.putNumber("pred2", predirection2);
SmartDashboard.putNumber("number1", steardrect1);
SmartDashboard.putNumber("number2", steardrect2);
SmartDashboard.putNumber("number3", steardrect3);
SmartDashboard.putNumber("number4", steardrect4);
SmartDashboard.putNumber("mod", speed*speedmod);
SmartDashboard.putNumber("tuen", work);
//*/
}


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
