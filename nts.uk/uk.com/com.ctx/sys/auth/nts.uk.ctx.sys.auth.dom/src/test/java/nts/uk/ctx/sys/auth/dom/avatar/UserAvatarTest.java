package nts.uk.ctx.sys.auth.dom.avatar;

import org.junit.Test;

import mockit.Mocked;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAvatarTest {

	@Mocked
	private static UserAvatarDto mockDto = UserAvatarDto.builder().personalId("personalId").fileId("fileId").build();;

	@Test
	public void createFromMementoAndGetMemento() {
		// when
		UserAvatar domain = UserAvatar.createFromMemento(mockDto);

		// then
		assertThat(domain.getPersonalId()).isEqualTo(mockDto.getPersonalId());
		assertThat(domain.getFileId()).isEqualTo(mockDto.getFileId());
	}

	@Test
	public void setMemento() {
		// given
		UserAvatarDto avtarDto = UserAvatarDto.builder().build();
		UserAvatar domain = UserAvatar.createFromMemento(mockDto);

		// when
		domain.setMemento(avtarDto);

		// then
		assertThat(domain.getPersonalId()).isEqualTo(avtarDto.getPersonalId());
		assertThat(domain.getFileId()).isEqualTo(avtarDto.getFileId());
	}
}
