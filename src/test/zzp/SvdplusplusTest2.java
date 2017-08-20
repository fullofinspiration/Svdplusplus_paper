package test.zzp;

import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.data.model.TextDataModel;
import net.librec.eval.RecommenderEvaluator;
import net.librec.eval.rating.RMSEEvaluator;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.cf.rating.SVDPlusPlusRecommender;

public class SvdplusplusTest2 {
	static Configuration conf = new Configuration();

	public static void main(String[] args) throws LibrecException {
		// build data model

		conf.set("dfs.data.dir",
				"/home/students/2016/PG/zhangzp/rating");
		conf.set("data.input.path", "");
		TextDataModel dataModel = new TextDataModel(conf);
		dataModel.buildDataModel();

		// build recommender context
		RecommenderContext context = new RecommenderContext(conf, dataModel);
		conf.set("rec.recommender.class", "svdpp");
		conf.set("rec.iterator.learnrate", "0.001");
		conf.set("rec.iterator.learnrate.maximum", "0.01");
		conf.set("rec.iterator.maximum", "50");
		conf.set("rec.user.regularization", "0.01");
		conf.set("rec.user.regularization", "0.01");
		conf.set("rec.impItem.regularization", "0.001");
		conf.set("rec.factor.number", "10");
		conf.set("rec.learnrate.bolddriver", "false");
		conf.set("rec.learnrate.decay", "1.0");

		Recommender recommender = new SVDPlusPlusRecommender();
		recommender.setContext(context);
		RecommenderEvaluator evaluator = new RMSEEvaluator();
		for (int i = 1; i < 10; i++) {
			Double j = i * 0.01;
			String k = j.toString();
			conf.set("rec.iterator.learnrate", k);
			for (int l = 1; l < 10; l++) {
				Double m = l * 0.01;
				String n = m.toString();
				conf.set("rec.user.regularization", n);
				for (int o = 1; o < 10; o++) {
					Double p = o * 0.01;
					String q = p.toString();
					conf.set("rec.item.regularization", q);
					// run recommender algorithm
					try {
						recommender.recommend(context);
					} catch (Exception e) {
						continue;
					}

					// evaluate the recommended result

					System.out.print("RMSE:" + recommender.evaluate(evaluator));
					System.out.print(" learnrate:"
							+ conf.get("rec.iterator.learnrate"));
					System.out.print(" userregularization:"
							+ conf.get("rec.user.regularization"));
					System.out.println(" itemretularization:"
							+ conf.get("rec.item.regularization"));

				}

			}

		}

	}

}
