using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Build4Cloud
{
    class Context
    {
        public string RootPath { get; set; }

        public string Project { get; set; }

        public string GetPathToPersistenceXml()
        {
            return Path.Combine(
                GetPathToWeb(),
                "src",
                "main",
                "resources",
                "META-INF",
                "persistence.xml");
        }

        public string GetPathToWeb()
        {
            return Path.Combine(
                RootPath,
                $"uk.{Project}",
                $"{Project}.web",
                $"nts.uk.{Project}.web");
        }
    }
}
