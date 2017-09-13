package nts.uk.shr.infra.i18n.loading;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;

import nts.arc.i18n.custom.ISessionLocale;
import nts.arc.i18n.custom.LanguageChangedEvent;
import nts.arc.i18n.custom.ResourceChangedEvent;
import nts.gul.misc.DeepClonable;
import nts.gul.text.IdentifierUtil;

public class SessionLocale implements ISessionLocale,Serializable,DeepClonable<SessionLocale>{
	private Locale currentLocale;
	private long version;

	public SessionLocale() {
		//TODO: temp fix for test
		this.currentLocale = Locale.JAPANESE;
		version = IdentifierUtil.randomUniqueId().hashCode();
	}

	private Event<LanguageChangedEvent> languageChanged;

	@Override
	public Locale getSessionLocale() {
		return this.currentLocale;
	}

	@Override
	public void setSessionLocale(Locale locale) {
		this.currentLocale = locale;

		LanguageChangedEvent event = new LanguageChangedEvent();
		languageChanged.fire(event);

	}

	@Override
	public void resourceChangedEventHandler(ResourceChangedEvent event) {
		version++;
	}

	@Override
	public long getVersion() {
		// TODO Auto-generated method stub
		return this.version;
	}

	@Override
	public SessionLocale deepClone() {
		SessionLocale temp  = new SessionLocale();
		temp.currentLocale = this.currentLocale;
		temp.version = this.version;
		return temp;
	}
}
