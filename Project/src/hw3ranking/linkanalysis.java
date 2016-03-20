package hw3ranking; 
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "searchcontrol", urlPatterns = {"/searchcontrol"})
public class searchcontrol extends HttpServlet {
	public static boolean ASC = true;
	public static boolean DESC = false;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {


			try
			{  
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Servlet searchcontrol</title>");            
				out.println("</head>");
				out.println("<body>");

				String query=request.getParameter("txtSearch");

				out.println("query=="+query);

				String box=request.getParameter("drpSearch");

				out.println("query=="+box);

                              if(box!=null)
                              {   
				



					HashMap <Integer, HashMap> hm=  new controller.Connexion(). getTFIDF();



					for(int i=0;i<hm.size();i++)
					{
						int id= hm.get(i);

						Double linkrank=  new controller.Connexion().getlinkAnalysis(id);
						String filepath = new controller.Connexion().getFilePath(id);
						out.println("id=="+id);

						Double linknor= new normalize().normalize(linkrank)*0.7;
						Double tfrank=  new controller.Connexion().gettfidf(id, query);
                                                
                                                
                                                MathContext mc = new MathContext(4);
                                                Decimal bd1 = new Decimal(new normalize().normalize(linkrank));
                                                Decimal bd2 = new Decimal(0.7d);

                                                Decimal result1 = bd1.multiply(bd2,mc);
                                                 System.out.print("value123=="+result1.doubleValue());
                                                
						Double tfnor= new normalize().normalize(tfrank) *0.3;

                                                Decimal bd3 = new Decimal(new normalize().normalize(tfrank));
                                                Decimal bd4 = new Decimal(0.3d);

                                                BigDecimal result2 = bd3.multiply(bd4,mc);

                                                 System.out.print("value456=="+result2);


                                                 Decimal sum=result1.add(result2, mc);
						hm.put(Integer.toString(id) + "~" + filepath + "~" + linkrank + "~" + tfrank,sum.doubleValue());


					}

					HashMap <Integer, HashMap> hm=  new controller.Connexion(). getTFIDF();
					getServletContext().setAttribute("query", sortedMapAsc);

					RequestDispatcher r=request.getRequestDispatcher("view.jsp");
					r.forward(request, response);








				}



			}

			catch(Exception ae)
			{
			System.	out.println(ae);
			System.out.println(ae.getMessage());

			}







			out.println("</body>");
			out.println("</html>");
		}
	}


	private static Map<Integer, HashMap> sortByComparator(Map<Integer, HashMap> unsortMap, final boolean order)
	{

		List<Map.Entry<Integer, HashMap>> list = new LinkedList<Map.Entry<Integer, HashMap>>(unsortMap.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<Integer, HashMap>>()
		{
			public int compare(Map.Entry<Integer, HashMap> o1,
					Map.Entry<Integer,HashMap> o2)
			{
				if (order)
				{
					return o1.getValue().compareTo(o2.getValue());
				}
				else
				{
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});

		Map<Integer, HashMap> sortedMap = new LinkedHashMap<>();
		for (Map.Entry<Integer,HashMap> entry : list)
		{
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}





	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}




	public static void printMap(Map<Integer, HashMap>  map)
	{
		for (Map.Entry<Integer, HashMap>  entry : map.entrySet())
		{
			System.out.println("Key : " + entry.getKey() + " Value : "+ entry.getValue());
		}
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	
	@Override
	public String getServletInfo() {
		return "Short description";
	}

}