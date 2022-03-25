using System;

namespace WSISmile.Business.Enum
{
    /// <summary>
    /// Web�T�[�r�X
    /// </summary>
    public enum WebApi
    {
        /// <summary>
        /// ���O�C��
        /// </summary>
        Login,
        /// <summary>
        /// �_����`�F�b�N
        /// </summary>
        ContractCheck,
        /// <summary>
        /// �O���o�͋N��
        /// </summary>
        OutputStarting,
        /// <summary>
        /// �O���o�͐ݒ�����擾
        /// </summary>
        GetOutputSetting,
        /// <summary>
        /// �O���o�͏�����ԃ`�F�b�N
        /// </summary>
        OutputStatusCheck,
        /// <summary>
        /// �O���o�͈ꎞ�t�@�C����FileID���擾
        /// </summary>
        GetOutputTempFileId,
        /// <summary>
        /// �O�����->����
        /// </summary>
        AcceptPrepare,
        /// <summary>
        /// �O������N��
        /// </summary>
        AcceptStarting,
        /// <summary>
        /// �O�����������ԃ`�F�b�N
        /// </summary>
        AcceptStatusCheck,
        /// <summary>
        /// �O������G���[���`�F�b�N
        /// </summary>
        AcceptErrorInfoCheck,
        /// <summary>
        /// �t�@�C���_�E�����[�h����
        /// </summary>
        FileDownload,
        /// <summary>
        /// �t�@�C���A�b�v���[�h����
        /// </summary>
        FileUpload,
        /// <summary>
        /// ���ߏ����擾
        /// </summary>
        GetClosureInfo,
        /// <summary>
        /// �ٗp�^���ߏ����擾
        /// </summary>
        GetEmploymentClosureInfo,
        /// <summary>
        /// ���ߊ���(�w��N��)*����ID�P�ʂ��擾
        /// </summary>
        GetClosurePeriod,
        /// <summary>
        /// ���ʎ��т̒��ߏ����擾
        /// </summary>
        GetMonthlyClosureInfo,
        /// <summary>
        /// ���ʎ��т̃��b�N��Ԃ̃`�F�b�N
        /// </summary>
        MonthlyLockStatusCheck,
        /// <summary>
        /// ���ʎ��т̏��F��Ԃ̃`�F�b�N
        /// </summary>
        MonthlyApproveStatusCheck,
        /// <summary>
        /// Smile�A�g-�O���o�͐ݒ�����擾
        /// </summary>
        GetSmileOutputSetting,
        /// <summary>
        /// Smile�A�g-�O������ݒ�����擾
        /// </summary>
        GetSmileAcceptSetting,
        /// <summary>
        /// �w��ٗp(���ߊ���)�ɂ��Ј����o
        /// </summary>
        SelectEmployeesByEmployment
    }
}
