LPhyBEAST | LPhy | BEAST 2
--- | --- | ---
**lphybeast.GeneratorToBEAST** |  |  
lphybeast.tobeast.generators.SkylineToBSP | lphy.evolution.coalescent.SkylineCoalescent | beast.evolution.tree.coalescent.BayesianSkyline
lphybeast.tobeast.generators.SerialCoalescentToBEAST | lphy.evolution.coalescent.SerialCoalescent | beast.evolution.tree.coalescent.Coalescent
lphybeast.tobeast.generators.GTRToBEAST | lphy.evolution.substitutionmodel.GTR | substmodels.nucleotide.GTR
lphybeast.tobeast.generators.HKYToBEAST | lphy.evolution.substitutionmodel.HKY | beast.evolution.substitutionmodel.HKY
lphybeast.tobeast.generators.ExpToBEAST | lphy.core.distributions.Exp | beast.math.distributions.Prior
lphybeast.tobeast.generators.ExpMarkovChainToBEAST | lphy.core.distributions.ExpMarkovChain | beast.math.distributions.MarkovChainDistribution
lphybeast.tobeast.generators.LogNormalMultiToBEAST | lphy.core.distributions.LogNormalMulti | beast.math.distributions.Prior
lphybeast.tobeast.generators.MultispeciesCoalescentToBEAST | lphy.evolution.coalescent.MultispeciesCoalescent | beast.evolution.speciation.GeneTreeForSpeciesTreeDistribution
lphybeast.tobeast.generators.DirichletToBEAST | lphy.core.distributions.Dirichlet | beast.math.distributions.Prior
lphybeast.tobeast.generators.BirthDeathSampleTreeDTToBEAST | lphy.evolution.birthdeath.BirthDeathSamplingTreeDT | beast.evolution.speciation.BirthDeathGernhard08Model
lphybeast.tobeast.generators.StructuredCoalescentToMascot | lphy.evolution.coalescent.StructuredCoalescent | beast.mascot.distribution.Mascot
lphybeast.tobeast.generators.GammaToBEAST | lphy.core.distributions.Gamma | beast.math.distributions.Prior
lphybeast.tobeast.generators.BetaToBEAST | lphy.core.distributions.Beta | beast.math.distributions.Prior
lphybeast.tobeast.generators.LogNormalToBEAST | lphy.core.distributions.LogNormal | beast.math.distributions.Prior
lphybeast.tobeast.generators.K80ToBEAST | lphy.evolution.substitutionmodel.K80 | beast.evolution.substitutionmodel.HKY
lphybeast.tobeast.generators.NormalToBEAST | lphy.core.distributions.Normal | beast.math.distributions.Prior
lphybeast.tobeast.generators.NormalMultiToBEAST | lphy.core.distributions.NormalMulti | beast.math.distributions.Prior
lphybeast.tobeast.generators.CoalescentToBEAST | lphy.evolution.coalescent.Coalescent | beast.evolution.tree.coalescent.Coalescent
lphybeast.tobeast.generators.PhyloCTMCToBEAST | lphy.evolution.likelihood.PhyloCTMC | beast.evolution.likelihood.TreeLikelihood
lphybeast.tobeast.generators.JukesCantorToBEAST | lphy.evolution.substitutionmodel.JukesCantor | beast.evolution.substitutionmodel.JukesCantor
lphybeast.tobeast.generators.YuleToBEAST | lphy.evolution.birthdeath.Yule | beast.evolution.speciation.YuleModel
lphybeast.tobeast.generators.F81ToBEAST | lphy.evolution.substitutionmodel.F81 | beast.evolution.substitutionmodel.HKY
lphybeast.tobeast.generators.TN93ToBEAST | lphy.evolution.substitutionmodel.TN93 | beast.evolution.substitutionmodel.TN93
**lphybeast.ValueToBEAST** |  |  
lphybeast.tobeast.values.IntegerValueToBEAST | java.lang.Integer | beast.core.parameter.IntegerParameter
lphybeast.tobeast.values.DoubleValueToBEAST | java.lang.Double | beast.core.parameter.RealParameter
lphybeast.tobeast.values.IntegerArrayValueToBEAST | [Ljava.lang.Integer; | beast.core.parameter.IntegerParameter
lphybeast.tobeast.values.TimeTreeToBEAST | lphy.evolution.tree.TimeTree | beast.util.TreeParser
lphybeast.tobeast.values.DoubleArray2DValueToBEAST | [[Ljava.lang.Double; | beast.core.parameter.RealParameter
lphybeast.tobeast.values.MapValueToBEAST | java.util.Map | beast.core.parameter.RealParameter
lphybeast.tobeast.values.DoubleArrayValueToBEAST | [Ljava.lang.Double; | beast.core.parameter.RealParameter
lphybeast.tobeast.values.AlignmentToBEAST | lphy.evolution.alignment.Alignment | beast.evolution.alignment.Alignment
**Suspected missing parser** | **Implemented LPhy** | **Implemented BEAST**
|  | lphy.core.functions.DoubleArray | 
|  | lphy.core.functions.ARange | 
|  | lphy.core.functions.Log | 
|  | lphy.core.functions.Newick | 
|  | lphy.core.functions.Range | 
|  | lphy.core.functions.Exp | 
|  | lphy.core.functions.NodeCount | 
|  | lphy.core.functions.BinaryRateMatrix | 
|  | lphy.core.functions.MigrationCount | 
|  | lphy.core.functions.CoalescentCorrection | 
|  | lphy.core.functions.Pow | 
|  | lphy.core.functions.NTaxa | 
|  | lphy.core.functions.Map | 
|  | lphy.core.functions.IntegerArray | 
|  | lphy.core.functions.Rep | 
|  | lphy.core.functions.RootAge | 
|  | lphy.core.functions.Floor | 
|  | lphy.core.functions.MigrationMatrix | 
|  | lphy.core.lightweight.GenerativeDistributionAdapter | 
|  | lphy.graphicalModel.types.WrappedDoubleValue$WrappedDoubleGenerator | 
|  | lphy.toroidalDiffusion.DihedralAngleDiffusionMatrix | 
|  | lphy.parser.ExpressionNode1Arg | 
|  | lphy.parser.ExpressionNode2Args | 
|  | lphy.parser.ExpressionNodeWrapper | 
|  | lphy.graphicalModel.RandomVariable | 
|  | lphy.graphicalModel.types.StringValue | 
|  | lphy.graphicalModel.types.IntegerValue | 
|  | lphy.graphicalModel.types.NumberValue | 
|  | lphy.graphicalModel.types.NumberArrayValue | 
|  | lphy.graphicalModel.types.StringArray2DValue | 
|  | lphy.graphicalModel.types.StringArrayValue | 
|  | lphy.graphicalModel.types.DoubleArrayValue | 
|  | lphy.graphicalModel.types.IntegerArray2DValue | 
|  | lphy.graphicalModel.types.DoubleArray2DValue | 
|  | lphy.graphicalModel.types.IntegerArrayValue | 
|  | lphy.graphicalModel.types.DoubleValue | 
|  | lphy.graphicalModel.types.WrappedDoubleValue | 
|  |  | beast.app.seqgen.SimulatedAlignment
|  |  | beast.evolution.alignment.AscertainedAlignment
|  |  | beast.evolution.alignment.FilteredAlignment
|  |  | beast.evolution.substitutionmodel.BinaryCovarion
|  |  | beast.evolution.substitutionmodel.Blosum62
|  |  | beast.evolution.substitutionmodel.CPREV
|  |  | beast.evolution.substitutionmodel.Dayhoff
|  |  | beast.evolution.substitutionmodel.GTR
|  |  | beast.evolution.substitutionmodel.GeneralSubstitutionModel
|  |  | beast.evolution.substitutionmodel.JTT
|  |  | beast.evolution.substitutionmodel.MTREV
|  |  | beast.evolution.substitutionmodel.MutationDeathModel
|  |  | beast.evolution.substitutionmodel.SYM
|  |  | beast.evolution.substitutionmodel.TIM
|  |  | beast.evolution.substitutionmodel.TVM
|  |  | beast.evolution.substitutionmodel.WAG
|  |  | beast.evolution.sitemodel.SiteModel
|  |  | beast.evolution.branchratemodel.RandomLocalClockModel
|  |  | beast.evolution.branchratemodel.StrictClockModel
|  |  | beast.evolution.branchratemodel.UCRelaxedClockModel
|  |  | beast.math.distributions.Beta
|  |  | beast.math.distributions.ChiSquare
|  |  | beast.math.distributions.Dirichlet
|  |  | beast.math.distributions.Exponential
|  |  | beast.math.distributions.Gamma
|  |  | beast.math.distributions.InverseGamma
|  |  | beast.math.distributions.LaplaceDistribution
|  |  | beast.math.distributions.LogNormalDistributionModel
|  |  | beast.math.distributions.Normal
|  |  | beast.math.distributions.OneOnX
|  |  | beast.math.distributions.Poisson
|  |  | beast.math.distributions.Uniform
|  |  | beast.core.parameter.BooleanParameter
|  |  | beast.core.parameter.GeneralParameterList$QuietParameter
|  |  | beast.core.util.CompoundDistribution
|  |  | beast.evolution.likelihood.BeagleTreeLikelihood
|  |  | beast.evolution.likelihood.GenericTreeLikelihood
|  |  | beast.evolution.likelihood.ThreadedTreeLikelihood
|  |  | beast.evolution.speciation.CalibratedBirthDeathModel
|  |  | beast.evolution.speciation.CalibratedYuleModel
|  |  | beast.evolution.speciation.SpeciesTreeDistribution
|  |  | beast.evolution.speciation.SpeciesTreePopFunction
|  |  | beast.evolution.speciation.SpeciesTreePrior
|  |  | beast.evolution.tree.TreeDistribution
|  |  | beast.math.distributions.MRCAPrior
|  |  | beast.evolution.datatype.Aminoacid
|  |  | beast.evolution.datatype.Binary
|  |  | beast.evolution.datatype.IntegerData
|  |  | beast.evolution.datatype.Nucleotide
|  |  | beast.evolution.datatype.StandardData
|  |  | beast.evolution.datatype.TwoStateCovarion
|  |  | beast.evolution.datatype.UserDataType
