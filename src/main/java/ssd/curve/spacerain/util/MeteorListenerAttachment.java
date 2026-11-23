package ssd.curve.spacerain.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.valkyrienskies.core.api.ships.PhysShip;
import org.valkyrienskies.core.api.ships.ShipPhysicsListener;
import org.valkyrienskies.core.api.world.PhysLevel;
import org.valkyrienskies.mod.mixinducks.world.entity.PlayerDuck;

public final class MeteorListenerAttachment implements ShipPhysicsListener {
    public double previousspeed;
    private boolean keepworking;
    private boolean stopbeingstatic;
    private boolean attemptcrater;
    private int ptickcountermajig;
    private Vec2 shift;
    public MeteorListenerAttachment() {
        this.previousspeed = 0;
        this.keepworking = true;
        this.stopbeingstatic = false;
        this.attemptcrater = false;
        this.ptickcountermajig = 0;
        float shiftmul = (float) (Math.random() * 2 * Math.PI);
        float shiftdeg = (float) Math.random() * 10 + 5;
        this.shift = new Vec2((float) Math.sin(shiftdeg) * shiftmul, (float) Math.cos(shiftdeg) * shiftmul);
    };

    @Override
    public void physTick(@NotNull PhysShip physShip, @NotNull PhysLevel physLevel) {
        // do stuff

        // ok 2 things
        // if too far away, stop chunkloading (?)
        // if velocity drops suddenly, make crater and stop chunkloading
        if (this.keepworking) {
            this.ptickcountermajig += 1;
            double changeinvelocity = Math.abs(this.previousspeed - physShip.getVelocity().length());

            if (changeinvelocity > 4.0) { //?? why crash idfk
                this.attemptcrater = true;

                physShip.setStatic(true);

                this.keepworking = false;
                this.stopbeingstatic = true;
            }
            if (ptickcountermajig == 120) {
                physShip.applyBodyForce(new Vector3d(shift.x, 1, shift.y), physShip.getCenterOfMass()); // probably doesnt work at all lmfaooo
            }

            this.previousspeed = physShip.getVelocity().length();
            if (ptickcountermajig % 3 == 0) {
                if (!MeteorImpactCraterManager.dontFUCKINGmodifytheselists) {
                    MeteorImpactCraterManager.meteorfallparticles.add(physShip.getId());
                }

            }

        }
        if (this.stopbeingstatic) {

            physShip.setStatic(false);
        }
        if (this.attemptcrater) {
            if (!MeteorImpactCraterManager.dontFUCKINGmodifytheselists) {
                MeteorImpactCraterManager.meteorsExploding.add(physShip.getId());
                this.attemptcrater = false;
            }
        }


    }

}
