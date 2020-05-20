public class RedGifsRipper extends AbstractHTMLRipper {
	
	Public RedGifsRipper(URL url) throws IOException {
		super(url);
	}
	
	@Override
	Public String getHost() {
		return "redgifs";
	}
	
	@Override
	public String getDomain() {
		return "redgifs.com";
	}
	
	@Override
    public String getGID(URL url) throws MalformedURLException {
        Pattern p = Pattern.compile("^https?://(thumbs\\.|[wm\\.]*)redgifs\\.com/watch/@?([a-zA-Z0-9]+).*$");
        Matcher m = p.matcher(url.toExternalForm());
        
        if (m.matches())
            return m.group(2);
        
        throw new MalformedURLException(
                "Expected redgifs.com format: "
                        + "redgifs.com/id or "
                        + "thumbs.redgifs.com/id.gif"
                        + " Got: " + url);
    }

    private String stripHTMLTags(String t) {
        t = t.replaceAll("<html>\n" +
                " <head></head>\n" +
                " <body>", "");
        t = t.replaceAll("</body>\n" +
                "</html>", "");
        t = t.replaceAll("\n", "");
        t = t.replaceAll("=\"\"", "");
        return t;
    }
	
	@Override
	public Document getFirstPage() throws IOException {
		return Http.url(url).get();
	}
	
	@Override
    public String normalizeUrl(String url) {
        // Remove the date sig from the url
        return url.replaceAll("/[A-Z0-9]{8}/", "/");
    }
	
	
	@Override
    public List<String> getURLsFromPage(Document doc) {
        List<String> result = new ArrayList<String>();
        for (Element el : doc.select("img")) {
            result.add(el.attr("src"));
        }
        return result
    }
	
	
	@Override
    public void downloadURL(URL url, int index) {
        addURLToDownload(url, getPrefix(index));
    }
	
	
	
