package com.cgvsu.writer;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ObjWriter {

    public static void writeToFile(Model model, File file) throws IOException {
        String str = "";

        str += writeVertexes(model.vertices);
        str += writeTextureVertexes(model.textureVertices);
        str += writeNormals(model.normals);
        str += writePolygons(model.polygons);

        toFile(str, file.getAbsolutePath());
    }

    protected static void toFile(String line, String fileName) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(fileName);
        printWriter.print(line);
        printWriter.close();
    }

    protected static String writeVertexes(final List<Vector3f> v){
        String str = "";
        for (int i = 0; i < v.size(); i++){
            final String vx = String.format("%.4f", v.get(i).getX()).replace(',', '.');
            final String vy = String.format("%.4f", v.get(i).getY()).replace(',', '.');
            final String vz = String.format("%.4f", v.get(i).getZ()).replace(',', '.');
            str = str + "v  " + vx + " " + vy + " " + vz + "\n";
        }
        str = str + "# " + v.size() + " vertices";
        str+="\n";
        str+="\n";
        return str;
    }

    protected static String writeTextureVertexes(final List<Vector2f> vt){
        String str = "";
        for (int i = 0; i < vt.size(); i++){
            final String vtx = String.format("%.4f", vt.get(i).getX()).replace(',', '.');
            final String vty = String.format("%.4f", vt.get(i).getY()).replace(',', '.');
            str = str + "vt " + vtx + " " + vty + " " + "0.0000" + "\n";
        }
        str = str + "# " + vt.size() + " texture coords";
        str+="\n";
        str+="\n";
        return str;
    }

    protected static String writeNormals(final List<Vector3f> vn){
        String str = "";
        for (int i = 0; i < vn.size(); i++){
            final String vx = String.format("%.4f", vn.get(i).getX()).replace(',', '.');
            final String vy = String.format("%.4f", vn.get(i).getY()).replace(',', '.');
            final String vz = String.format("%.4f", vn.get(i).getZ()).replace(',', '.');
            str = str + "vn  " + vx + " " + vy + " " + vz + "\n";
        }
        str = str + "# " + vn.size() + " normals";
        str+="\n";
        str+="\n";
        return str;
    }

    protected static String writePolygons(final List<Polygon> p){
        String str = "";
        for (int i = 0; i < p.size(); i++){
            str = str + "f ";
            final Polygon pol = p.get(i);
            for (int j = 0; j < pol.getVertexIndices().size(); j++){
                if (!pol.getTextureVertexIndices().isEmpty() && pol.getNormalIndices().isEmpty()){
                    str = str  + (pol.getVertexIndices().get(j) + 1) + "/"
                           + (pol.getTextureVertexIndices().get(j) + 1) + " ";
                }
                if (pol.getTextureVertexIndices().isEmpty() && pol.getNormalIndices().isEmpty()){
                    str = str  + (pol.getVertexIndices().get(j) + 1) +  " ";
                }
                if (!pol.getTextureVertexIndices().isEmpty() && !pol.getNormalIndices().isEmpty()){
                    str = str  + (pol.getVertexIndices().get(j) + 1) + "/"
                            + (pol.getTextureVertexIndices().get(j) + 1) + "/"
                            + (pol.getNormalIndices().get(j) + 1) + " ";
                }
                if (pol.getTextureVertexIndices().isEmpty() && !pol.getNormalIndices().isEmpty()){
                    str = str  + (pol.getVertexIndices().get(j) + 1) + "//"
                            + (pol.getNormalIndices().get(j) + 1) + " ";
                }
            }
            str = str  + "\n";
        }
        str = str + "# " + p.size() + " polygons";
        str+="\n";
        str+="\n";
        return str;
    }

}
