package org.rcsb.biozernike;

import org.biojava.nbio.structure.*;
import org.biojava.nbio.structure.io.*;

import org.rcsb.biozernike.volume.Volume;

import javax.vecmath.Point3d;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java -jar biozernike.jar <file_path>");
            System.exit(1);
        }

        String filePath = args[0];

        Structure structure = StructureIO.getStructure(filePath);
        Atom[] reprAtoms = StructureTools.getRepresentativeAtomArray(structure);
        Point3d[] reprPoints = Arrays.stream(reprAtoms)
                .map(atom -> new Point3d(atom.getCoords()))
                .toArray(Point3d[]::new);
        String[] resNames = Arrays.stream(reprAtoms)
                .map(atom -> atom.getGroup().getPDBName())
                .toArray(String[]::new);
        Volume volumeStructure = new Volume();
        volumeStructure.create(reprPoints, resNames);
        InvariantNorm normalizationStructure = new InvariantNorm(volumeStructure, 6);
        System.out.println(filePath + "|=|2|=|" + normalizationStructure.getInvariants(2));
        System.out.println(filePath + "|=|3|=|" + normalizationStructure.getInvariants(3));
        System.out.println(filePath + "|=|4|=/" + normalizationStructure.getInvariants(4));
        System.out.println(filePath + "|=|5|=|" + normalizationStructure.getInvariants(5));
        System.out.println(filePath + "|=|6|=|" + normalizationStructure.getInvariants(6));
    }
}