using System;

namespace WSISmile.Business.Enum
{
    /// <summary>
    /// �O���o�͏������Enum
    /// </summary>
    public enum OutputStatus
    {
        /// <summary>
        /// �Ȃ�
        /// </summary>
        None = -1,
        /// <summary>
        /// 0�F������
        /// </summary>
        Preparing = 0,
        /// <summary>
        /// 1�F�o�͒�
        /// </summary>
        Outputing = 1,
        /// <summary>
        /// 2�F�����
        /// </summary>
        Accepting = 2,
        /// <summary>
        /// 3�F�e�X�g����
        /// </summary>
        TestFinished = 3,
        /// <summary>
        /// 4�F���f�I��
        /// </summary>
        Suspension = 4,
        /// <summary>
        /// 5�F�ُ�I��
        /// </summary>
        FinishedByError = 5,
        /// <summary>
        /// 6�F�`�F�b�N��
        /// </summary>
        Checking = 6,
        /// <summary>
        /// 7�F�o�͊���
        /// </summary>
        OutputFinished = 7,
        /// <summary>
        /// 8�F�������
        /// </summary>
        AcceptFinished = 8
    }
}
