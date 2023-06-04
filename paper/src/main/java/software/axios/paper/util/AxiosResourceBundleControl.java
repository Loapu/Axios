package software.axios.paper.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public final class AxiosResourceBundleControl extends ResourceBundle.Control
{
	@Override
	public List<String> getFormats(String baseName) {
		return List.of("properties");
	}
	
	@Override
	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IOException, IllegalAccessException, InstantiationException
	{
		ResourceBundle bundle = null;
		
		String baseCustom = baseName + "_custom";
		String baseLanguage = baseName + "_" + locale.getLanguage();
		String baseLanguageCustom = baseLanguage + "_custom";
		String baseLanguageCountry = baseLanguage + "_" + locale.getCountry();
		String baseLanguageCountryCustom = baseLanguageCountry + "_custom";
		
		List<String> searchOrder = new ArrayList<>();
		
		searchOrder.add(toResourceName(baseLanguageCountryCustom, "properties"));
		searchOrder.add(toResourceName(baseLanguageCountry, "properties"));
		searchOrder.add(toResourceName(baseLanguageCustom, "properties"));
		searchOrder.add(toResourceName(baseLanguage, "properties"));
		searchOrder.add(toResourceName(baseCustom, "properties"));
		searchOrder.add(toResourceName(baseName, "properties"));
		
		for (String name : searchOrder)
		{
			InputStream stream = null;
			try
			{
				if (reload)
				{
					URL url = loader.getResource(name);
					if (url != null)
					{
						URLConnection connection = url.openConnection();
						if (connection != null)
						{
							connection.setUseCaches(false);
							stream = connection.getInputStream();
						}
					}
				}
				else
				{
					stream = loader.getResourceAsStream(name);
				}
				
				if (stream != null)
				{
					bundle = new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
					break;
				}
			}
			catch (IOException ignored)
			{
			}
			finally
			{
				if (stream != null)
				{
					stream.close();
				}
			}
			
		}
		return bundle;
	}
}
