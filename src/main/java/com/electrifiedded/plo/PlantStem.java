package com.electrifiedded.plo;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Stem that grows a vanilla ore block as its fruit (reimplementation of the 1.7.10 behaviour). */
public class PlantStem extends BlockStem {
    public final int color;
    public Item seeds;

    public PlantStem(Block ore, int color) {
        super(ore);
        this.color = color;
        setCreativeTab(null);
    }

    @Override
    protected Item getSeedItem() {
        return seeds == null ? Items.AIR : seeds;
    }

    /** the original stems drop nothing */
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    }

    @Override
    public void dropBlockAsItemWithChance(World w, BlockPos pos, IBlockState state, float chance, int fortune) {
    }

    @Override
    public ItemStack getItem(World w, BlockPos pos, IBlockState state) {
        return seeds == null ? ItemStack.EMPTY : new ItemStack(seeds);
    }
}
