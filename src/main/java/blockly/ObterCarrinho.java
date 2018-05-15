package blockly;

import cronapi.*;
import cronapi.rest.security.CronappSecurity;
import java.util.Iterator;
import java.util.concurrent.Callable;

@CronapiMetaData(type = "blockly")
@CronappSecurity
public class ObterCarrinho {

	public static final int TIMEOUT = 300;

	/**
	 */
	// Descreva esta função...
	public static void inserir() throws Exception {
		new Callable<Var>() {

			private Var carrinho = Var.VAR_NULL;
			private Var j = Var.VAR_NULL;

			public Var call() throws Exception {
				carrinho = cronapi.json.Operations
						.toJson(cronapi.screen.Operations.getValueOfField(Var.valueOf("vars.Lista")));
				for (Iterator it_j = carrinho.iterator(); it_j.hasNext();) {
					j = Var.valueOf(it_j.next());
					System.out.println(
							cronapi.object.Operations.getObjectField(j, Var.valueOf("pedido")).getObjectAsString());
					cronapi.database.Operations.insert(Var.valueOf("app.entity.Venda"),
							Var.valueOf("produto",
									cronapi.object.Operations.getObjectField(j, Var.valueOf("produto.id"))),
							Var.valueOf("data", cronapi.dateTime.Operations.getNow()),
							Var.valueOf("pedido", cronapi.object.Operations.getObjectField(j, Var.valueOf("pedido"))),
							Var.valueOf("quantidade",
									cronapi.object.Operations.getObjectField(j, Var.valueOf("quantidade"))));
				} // end for
				cronapi.util.Operations.callClientFunction(Var.valueOf("cronapi.screen.openUrl"),
						Var.valueOf("/webapp/#/home/logged/pedido"), Var.VAR_FALSE, Var.VAR_NULL, Var.VAR_NULL);
				return Var.VAR_NULL;
			}
		}.call();
	}

}
