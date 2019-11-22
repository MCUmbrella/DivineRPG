package divinerpg.utils;

import divinerpg.DivineRPG;
import divinerpg.registry.ItemRegistry;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.function.Supplier;

public class DivineArmorMaterial implements IArmorMaterial {
    public static final DivineArmorMaterial JACK_O_MAN = new DivineArmorMaterial("jack_o_man", -1, 0, 22,
            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0, Ingredient::fromItems);

    /**
     * @param name                   - name of armor material
     * @param chestplateDamage       - amount of chestplate damage. Others parts will be calculated by resources, needed to create armor piece
     * @param totalDefense           - amount of armor points. Diamond have 20 points that gives 80% defence
     * @param enchantabilityIn       - enchantability. 25 for gold, 10 by default
     * @param onEquip                - sound on equip
     * @param toughness              - toughness. 2 for Diamond
     * @param repairMaterialSupplier - reparing material
     */
    public DivineArmorMaterial(String name, int chestplateDamage, int totalDefense, int enchantabilityIn, SoundEvent onEquip, float toughness, Supplier<Ingredient> repairMaterialSupplier) {
        MAX_DAMAGE_ARRAY = new int[]{4, 7, 8, 5};
        this.damageReductionAmountArray = new int[]{0, 0, 0, 0};

        // damage by single ingot
        this.chestplateDamage = chestplateDamage / 8F;
        this.name = new ResourceLocation(DivineRPG.MODID, name).toString();
        this.enchantability = enchantabilityIn;
        this.soundEvent = onEquip;
        this.toughness = toughness;
        this.repairMaterial = new LazyLoadBase<>(repairMaterialSupplier);

        if (totalDefense < 1)
            return;

        // Total amount of ingots
        float defenceByIngot = (float) totalDefense / Arrays.stream(MAX_DAMAGE_ARRAY).sum();

        for (int i = 0; i < MAX_DAMAGE_ARRAY.length; i++) {
            int defencePoints = (int) Math.floor(defenceByIngot * MAX_DAMAGE_ARRAY[i]);
            damageReductionAmountArray[i] = defencePoints;
            totalDefense -= defencePoints;
        }

        // Some troubles while floor numbers
        if (totalDefense != 0) {
            damageReductionAmountArray[2] += totalDefense;
        }
    }

    private final int[] MAX_DAMAGE_ARRAY;
    private final String name;
    private final float chestplateDamage;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final LazyLoadBase<Ingredient> repairMaterial;

    public static DivineArmorMaterial forRupee(String color) {
        return new DivineArmorMaterial(color + "rupee", -1, 20, 15,
                SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2F, () -> Ingredient.fromItems(ItemRegistry.rupeeIngot));
    }

    public int getDurability(EquipmentSlotType slotIn) {
        return (int) (MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.chestplateDamage);
    }

    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }
}