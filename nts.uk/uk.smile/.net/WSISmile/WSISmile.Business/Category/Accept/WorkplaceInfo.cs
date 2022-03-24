using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;

using WSISmile.Business.Common;
using WSISmile.Business.Link;
using WSISmile.Business.Log;
using WSISmile.Business.Task;

namespace WSISmile.Business.Category.Accept
{
    /// <summary>
    /// �O�����-�g�D���J�e�S��
    /// </summary>
    public class WorkplaceInfo : AcceptCategoryBase
    {
        public WorkplaceInfo() { }

        #region CONST
        /// <summary>
        /// �K�w���x���ő�l:10
        /// </summary>
        public const int MAX_LEVEL = 10;

        /// <summary>
        /// �X�^�[�g���x�� (001)
        /// </summary>
        private const int LEVEL_START = 1;
        #endregion CONST

        /// <summary>
        /// Smile�g�D���-�K�w�č\�z
        /// </summary>
        /// <param name="dsSmile">Smile�g�D���</param>
        /// <param name="TI">�^�X�N���</param>
        /// <remarks>Smile�g�D���̊K�w������␳����(�K�w���x���Đݒ�)</remarks>
        private void RebuildSmileOrganization(DataSet dsSmile, TaskInfo TI)
        {
            #region Smile�g�D���-�K�w�č\�z : RebuildSmileOrganization
            Dictionary<int, int> levelSorter = new Dictionary<int, int>();
            // �K�w���x��[1]
            levelSorter.Add(1, 0);

            try
            {
                dsSmile.Tables[0].Columns.Add(SmileOrganizationItem.SORT, typeof(Int32));

                #region Sort�␳�O
                dsSmile.Tables[0].DefaultView.Sort = SmileOrganizationItem.COMPANY_CD + ", " +
                                                     SmileOrganizationItem.START_DAY + ", " +
                                                     SmileOrganizationItem.LEVEL + ", " +
                                                     SmileOrganizationItem.HIGHER_ORGANIZT_CD + ", " +
                                                     SmileOrganizationItem.ORGANIZATION_CD;
                #endregion Sort�␳�O

                DataTable dtSmile = dsSmile.Tables[0].DefaultView.ToTable();

                foreach (DataRow drChild in dtSmile.Rows)
                {
                    if (drChild[SmileOrganizationItem.LEVEL] != null)
                    {
                        if (drChild[SmileOrganizationItem.LEVEL].ToString() != "1")
                        {
                            #region �K�w���x��[1]�ȊO
                            foreach (DataRow drParent in dtSmile.Rows)
                            {
                                bool found = false;

                                // �q�̏�ʑg�D�R�[�h���e�̑g�D�R�[�h�@�ˁ@�e�̊K�w���x������(+1)���q�̊K�w���x���Ƃ���
                                if (drChild[SmileOrganizationItem.START_DAY] == drParent[SmileOrganizationItem.START_DAY] &&
                                    drChild[SmileOrganizationItem.HIGHER_ORGANIZT_CD] == drParent[SmileOrganizationItem.ORGANIZATION_CD])
                                {
                                    drChild[SmileOrganizationItem.LEVEL] = int.Parse(drParent[SmileOrganizationItem.LEVEL].ToString()) + 1;
                                    found = true;
                                    break;
                                }
                                // ������Ȃ������ꍇ�A�K�w���x��[1]
                                if (!found)
                                {
                                    drChild[SmileOrganizationItem.LEVEL] = 1;
                                }

                                int level = int.Parse(drChild[SmileOrganizationItem.LEVEL].ToString());
                                int sort = 0;
                                if (levelSorter.ContainsKey(level))
                                {
                                    sort = levelSorter[level];
                                }
                                else
                                {
                                    levelSorter.Add(level, sort);
                                }

                                drChild[SmileOrganizationItem.SORT] = sort;

                                levelSorter[level] = levelSorter[level] + 1;
                            }
                            #endregion
                        }
                        else
                        {
                            #region �K�w���x��[1]
                            drChild[SmileOrganizationItem.SORT] = levelSorter[1];
                            levelSorter[1] = levelSorter[1] + 1;
                            #endregion
                        }
                    }
                }

                #region Sort�␳��
                dtSmile.DefaultView.Sort = SmileOrganizationItem.COMPANY_CD + ", " + 
                                           SmileOrganizationItem.START_DAY + ", " + 
                                           SmileOrganizationItem.LEVEL + ", " + 
                                           SmileOrganizationItem.HIGHER_ORGANIZT_CD + ", " + 
                                           SmileOrganizationItem.SORT;
                #endregion Sort�␳��
            }
            catch (Exception ex)
            {
                TI.ErrorMsgList.Add("Smile�g�D���-�K�w�č\�z�����ɗ�O���������܂����B" + Environment.NewLine + ex.Message);
            }
            #endregion
        }

        /// <summary>
        /// Smile�g�D���� �K�w�č\�z �� �ҏW �� List.�֕ϊ�
        /// </summary>
        /// <param name="dsSmile">Smile�g�D���</param>
        /// <param name="TI">�^�X�N���</param>
        /// <returns>Smile�g�D���List.</returns>
        public List<SmileOrganization> SmileDataToList(DataSet dsSmile, TaskInfo TI)
        {
            #region Smile�g�D���� �K�w�č\�z �� �ҏW �� List.�֕ϊ� : SmileDataToList
            // Smile�g�D���-�K�w�č\�z
            this.RebuildSmileOrganization(dsSmile, TI);

            List<SmileOrganization> list = new List<SmileOrganization>();

            foreach (DataRow drSmile in dsSmile.Tables[0].Rows)
            {
                SmileOrganization smileOrganization = new SmileOrganization();

                // ��ЃR�[�h
                smileOrganization.CompanyCd = TI.CompCode;

                // �g�D�R�[�h
                smileOrganization.OrganizationCd = Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.ORGANIZATION_CD]);

                // ���ߔN����
                smileOrganization.StartDay = Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.START_DAY]);

                // �I���N����
                if (drSmile[SmileOrganizationItem.END_DAY].ToString() == "99999999")
                {
                    smileOrganization.EndDay = new DateTime(9999, 12, 31).ToString(Format.Date);
                }
                else
                {
                    smileOrganization.EndDay = Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.END_DAY]);
                }

                // ��ʑg�D�R�[�h
                smileOrganization.HigherOrganiztCd = Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.HIGHER_ORGANIZT_CD]);

                if (!Toolbox.IsNumber(drSmile[SmileOrganizationItem.LEVEL].ToString()))
                {
                    TI.ErrorMsgList.Add(this.GetErrorMessage(smileOrganization, "�K�w���x���ɐ����ł͂Ȃ��l���܂܂�Ă��܂��B"));
                    continue;
                }

                // �K�w���x��
                smileOrganization.Level = int.Parse(Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.LEVEL]));

                // �����g�D��
                smileOrganization.OrganiztNameOfficial = Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.ORGANIZT_NAME_OFFICIAL]);

                // �g�D��
                smileOrganization.OrganiztName = Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.ORGANIZT_NAME]);

                // �g�D����
                smileOrganization.OrganiztNameSimple = Toolbox.RemoveInvalidSymbol(drSmile[SmileOrganizationItem.ORGANIZT_NAME_SIMPLE]);

                list.Add(smileOrganization);
            }

            return list;
            #endregion
        }

        /// <summary>
        /// �E����̓����K�wCD[1�`10]��ݒ肷��
        /// </summary>
        /// <param name="listSmileOrganization">Smile�g�D���List.</param>
        /// <param name="TI"></param>
        public void SetWorkplaceInLevelCd(List<SmileOrganization> listSmileOrganization, TaskInfo TI)
        {
            #region �E����̓����K�wCD[1�`10]��ݒ肷�� : SetWorkplaceInLevelCd
            // �O����
            SmileOrganization previous = new SmileOrganization();

            try
            {
                foreach (SmileOrganization smileOrganization in listSmileOrganization)
                {
                    // TODO �Ύ��Y�̒��ߊJ�n�������O�̔��ߔN����("yyyy/MM/dd")��A�g����܂���ł����B
                    if (smileOrganization.Level > MAX_LEVEL)
                    {
                        // 10�K�w�ȏ�̏ꍇ�ɁA�����K�wCD[1]���N���A
                        smileOrganization.InlevelCds[0] = "";
                        continue;
                    }

                    if (smileOrganization.StartDay != previous.StartDay)
                    {
                        // ���ߔN�������قȂ�ꍇ�A�O������N���A
                        previous = new SmileOrganization();
                    }

                    if (smileOrganization.HigherOrganiztCd == "")
                    {
                        #region �K�w���x��[1] (��ʑg�D�R�[�h�����݂��Ȃ�)
                        if (smileOrganization.Level == previous.Level)
                        {
                            // �K�w���x��[1]�̏ꍇ�ɁA�J�E���g�A�b�v
                            smileOrganization.InlevelCds[0] = WorkplaceInfo.IntegerToInlevelCd((int.Parse(previous.InlevelCds[0]) + 1));
                        }
                        else
                        {
                            // First.
                            smileOrganization.InlevelCds[0] = WorkplaceInfo.IntegerToInlevelCd(LEVEL_START);
                        }
                        #endregion
                    }
                    else
                    {
                        #region �K�w���x��[1]�ȍ~
                        if (!SearchHigherOrganization(smileOrganization, listSmileOrganization))
                        {
                            // �����K�wCD[1]���N���A
                            smileOrganization.InlevelCds[0] = "";
                            TI.ErrorMsgList.Add(this.GetErrorMessage(smileOrganization, "��ʑg�D�R�[�h�̃f�[�^�����݂��܂���B"));
                            continue;
                        }

                        int index = smileOrganization.Level - 1;

                        if (smileOrganization.HigherOrganiztCd == previous.HigherOrganiztCd)
                        {
                            // ��ʑg�D�������̏ꍇ�ɁA�J�E���g�A�b�v
                            smileOrganization.InlevelCds[index] = WorkplaceInfo.IntegerToInlevelCd((int.Parse(previous.InlevelCds[index]) + 1));
                        }
                        else
                        {
                            // ��ʑg�D���قȂ�ꍇ�ɁA���Z�b�g
                            smileOrganization.InlevelCds[index] = WorkplaceInfo.IntegerToInlevelCd(LEVEL_START);
                        }
                        #endregion
                    }

                    // �O�����ۑ�
                    previous = smileOrganization.Clone();

                    // �����K�w�R�[�h��ݒ�
                    for (int i = 0; i < MAX_LEVEL; i++)
                    {
                        smileOrganization.InlevelCd += smileOrganization.InlevelCds[i];
                    }
                }

            }
            catch (Exception ex)
            {
                TI.ErrorMsgList.Add("�E����̓����K�wCD[1�`10]�ݒ菈���ɗ�O���������܂����B" + Environment.NewLine + ex.Message);
            }
            #endregion
        }

        /// <summary>
        /// �w��g�D�̏�ʑg�D���������A�����K�wCD[1�`10]���R�s�[
        /// </summary>
        /// <param name="searchOrganization">�����Ώۑg�D</param>
        /// <param name="listSmileOrganization">�S�g�D</param>
        /// <returns>true:���������^false:�������Ă��Ȃ�</returns>
        private bool SearchHigherOrganization(SmileOrganization searchOrganization, List<SmileOrganization> listSmileOrganization)
        {
            #region �w��g�D�̏�ʑg�D���������A�����K�wCD[1�`10]���R�s�[ : SearchHigherOrganization
            if (searchOrganization.Level == 1)
            {
                // TOP�K�w���x���̏ꍇ
                return true;
            }

            foreach (SmileOrganization smileOrganization in listSmileOrganization)
            {
                /*
                 * ���L�����𖞂����ꍇ�ɁA��ʑg�D�ƌ��Ȃ���ʑg�D�̓����K�wCD[1]�`�����K�wCD[10]�������Ώۑg�D�փR�s�[����
                 *  1) ���ߔN����������
                 *  2) �����g�D�̏�ʑg�D�R�[�h �� ���Y�g�D�̑g�D�R�[�h
                 *  3) �����g�D�̊K�w���x����ȏ� �� ���Y�g�D�̊K�w���x��
                 *  4) ���Y�g�D�̓����K�wCD[1]����ł͂Ȃ�
                */

                if (smileOrganization.StartDay == searchOrganization.StartDay &&
                    smileOrganization.OrganizationCd == searchOrganization.HigherOrganiztCd &&
                    smileOrganization.Level == searchOrganization.Level - 1 &&
                    smileOrganization.InlevelCds[0] != "")
                {
                    searchOrganization.InlevelCds = new List<string>(smileOrganization.InlevelCds);
                    return true;
                }
            }

            return false;
            #endregion
        }

        /// <summary>
        /// Smile�g�D���List.��A�gDataTable�֕ϊ�
        /// </summary>
        /// <param name="listSmile"></param>
        /// <returns></returns>
        public DataTable ListToSmileLinkTable(List<SmileOrganization> listSmile)
        {
            #region Smile�g�D���List.��A�gDataTable�֕ϊ� : ListToSmileLinkTable
            // Smile���A�gDataTable
            DataTable dtSmile = SmileOrganization.DefineSmileDataTable();

            foreach (SmileOrganization smileOrganization in listSmile)
            {
                DataRow drSmile = dtSmile.NewRow();

                // �J�n��
                drSmile[SmileOrganizationItem.START_DAY] = smileOrganization.StartDay;

                // �I����
                drSmile[SmileOrganizationItem.END_DAY] = smileOrganization.EndDay;

                // �E��R�[�h
                drSmile[SmileOrganizationItem.ORGANIZATION_CD] = smileOrganization.OrganizationCd;

                // �E�ꖼ��
                drSmile[SmileOrganizationItem.ORGANIZT_NAME] = smileOrganization.OrganiztName;

                // �E�ꗪ��
                drSmile[SmileOrganizationItem.ORGANIZT_NAME_SIMPLE] = smileOrganization.OrganiztNameSimple;

                // �E�ꑍ��
                drSmile[SmileOrganizationItem.ORGANIZT_NAME_OFFICIAL] = smileOrganization.OrganiztNameOfficial;

                // �E��K�w�R�[�h
                drSmile[SmileOrganizationItem.INLEVEL_CD] = smileOrganization.InlevelCd;

                #region �E��K�w�R�[�h 1 �` �E��K�w�R�[�h 10
                // �E��K�w�R�[�h 1
                drSmile[SmileOrganizationItem.INLEVEL_CD_1] = smileOrganization.InlevelCds[0];

                // �E��K�w�R�[�h 2
                drSmile[SmileOrganizationItem.INLEVEL_CD_2] = smileOrganization.InlevelCds[1];

                // �E��K�w�R�[�h 3
                drSmile[SmileOrganizationItem.INLEVEL_CD_3] = smileOrganization.InlevelCds[2];

                // �E��K�w�R�[�h 4
                drSmile[SmileOrganizationItem.INLEVEL_CD_4] = smileOrganization.InlevelCds[3];

                // �E��K�w�R�[�h 5
                drSmile[SmileOrganizationItem.INLEVEL_CD_5] = smileOrganization.InlevelCds[4];

                // �E��K�w�R�[�h 6
                drSmile[SmileOrganizationItem.INLEVEL_CD_6] = smileOrganization.InlevelCds[5];

                // �E��K�w�R�[�h 7
                drSmile[SmileOrganizationItem.INLEVEL_CD_7] = smileOrganization.InlevelCds[6];

                // �E��K�w�R�[�h 8
                drSmile[SmileOrganizationItem.INLEVEL_CD_8] = smileOrganization.InlevelCds[7];

                // �E��K�w�R�[�h 9
                drSmile[SmileOrganizationItem.INLEVEL_CD_9] = smileOrganization.InlevelCds[8];

                // �E��K�w�R�[�h 10
                drSmile[SmileOrganizationItem.INLEVEL_CD_10] = smileOrganization.InlevelCds[9];
                #endregion �E��K�w�R�[�h 1 �` �E��K�w�R�[�h 10

                dtSmile.Rows.Add(drSmile);
            }

            return dtSmile;
            #endregion
        }

        /// <summary>
        /// �E��K�w�R�[�h�t�H�[�}�b�g
        /// </summary>
        /// <param name="inlevelCd">�E��K�w�R�[�h</param>
        /// <returns></returns>
        private static string IntegerToInlevelCd(int inlevelCd)
        {
            #region �E��K�w�R�[�h�t�H�[�}�b�g : IntegerToInlevelCd
            return inlevelCd.ToString().PadLeft(3, '0');
            #endregion
        }

        /// <summary>
        /// �E��G���[���b�Z�[�W�̍쐬
        /// </summary>
        /// <returns>�G���[���b�Z�[�W</returns>
        private string GetErrorMessage(SmileOrganization smileOrganization, string appendMsg)
        {
            #region �E��G���[���b�Z�[�W�̍쐬 : GetErrorMessage
            return 
                "�g�D���F��ЃR�[�h=" + smileOrganization.CompanyCd + 
                " �g�D�R�[�h=" + smileOrganization.OrganizationCd + 
                " �g�D��=" + smileOrganization.OrganiztName + 
                " �� " + appendMsg;
            #endregion
        }
    }
}
