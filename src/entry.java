public class entry {
    int index;
    double RT;
    double mz;
    double MassDefect;
    double TIC;
    int NumMerged;
    int Quality;
    public entry(int indexIN, double RTIN, double mzIN, double MassDefectIN,
         double TICIN, int NumMergedIN, int QualityIN) {
        index = indexIN;
        RT = RTIN;
        mz = mzIN;
        MassDefect = MassDefectIN;
        TIC = TICIN;
        NumMerged = NumMergedIN;
        Quality = QualityIN;
    }
}
