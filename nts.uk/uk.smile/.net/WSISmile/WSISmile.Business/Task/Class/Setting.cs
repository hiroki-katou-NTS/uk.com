using System;

using WSISmile.Business.Link.Parameter.Setting;

namespace WSISmile.Business.Task
{
    /// <summary>
    /// Smile�A�g�ݒ���
    /// </summary>
    public class Setting
    {
        /// <summary>
        /// Smile�A�g-�O���o�͐ݒ���
        /// </summary>
        public SmileOutputSetting SmileOutputSetting = new SmileOutputSetting();

        /// <summary>
        /// Smile�A�g-�O������ݒ���
        /// </summary>
        public SmileAcceptSetting SmileAcceptSetting = new SmileAcceptSetting();
    }
}
