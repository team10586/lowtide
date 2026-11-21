package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// For follower class. Motor is: ALIGNED
public class Elevator extends SubsystemBase {
    private final TalonFX elevatorMotor_left = new TalonFX(11);
    private final TalonFX elevatorMotor_right = new TalonFX(12);

    private final MotionMagicVelocityVoltage request = new MotionMagicVelocityVoltage(0).withSlot(0);

    public Elevator() {
        var talonFXConfigs = new TalonFXConfiguration();
        var slot0Configs = talonFXConfigs.Slot0;
        slot0Configs.kP = 0.05;
        slot0Configs.kI = 0;
        slot0Configs.kD = 0;
        slot0Configs.kV = 0.5;
        slot0Configs.kA = 0.5;

        var motionMagicConfigs = talonFXConfigs.MotionMagic;
        // motionMagicConfigs.MotionMagicCruiseVelocity = 100;
        motionMagicConfigs.MotionMagicAcceleration = 100;

        var motorOutputConfigs = talonFXConfigs.MotorOutput;
        talonFXConfigs.CurrentLimits.SupplyCurrentLowerTime = 0;
        talonFXConfigs.CurrentLimits.SupplyCurrentLimitEnable = true;
        talonFXConfigs.CurrentLimits.SupplyCurrentLimit = 30;
        talonFXConfigs.CurrentLimits.StatorCurrentLimit = 40;
        talonFXConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
        
        motorOutputConfigs.Inverted = InvertedValue.CounterClockwise_Positive;
        motorOutputConfigs.NeutralMode = NeutralModeValue.Brake;
        
        elevatorMotor_left.getConfigurator().apply(talonFXConfigs);
        motorOutputConfigs.NeutralMode = NeutralModeValue.Coast;
        elevatorMotor_right.getConfigurator().apply(talonFXConfigs);
    }

    public void setElevatorSpeed(double speed) {
        request.Velocity = speed;
        elevatorMotor_left.setControl(request);
        // elevatorMotor_right.setControl(request);
    }
}
