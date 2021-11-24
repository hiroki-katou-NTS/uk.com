using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Build4Cloud
{
    class Program
    {
        static void Main(string[] args)
        {
            int dataSourcesCount;
            if (args.Length >= 1)
            {
                dataSourcesCount = int.Parse(args[0]);
            }
            else
            {
                dataSourcesCount = 1; // テスト用
            }

            IEnumerable<string> targetProjects;
            if (args.Length < 2)
            {
                targetProjects = new[] { "cloud" };
            }
            else
            {
                targetProjects = args.Skip(1);
            }

            var context = new Context
            {
                RootPath = Util.FindRootPathFromCurrent(),
                Projects = targetProjects,
            };

            var loader = new EntityManagerLoader(context.RootPath);
            loader.CreateCloudEdition(dataSourcesCount);

            foreach (var project in context.Projects)
            {
                string pathToWeb = Path.Combine(context.RootPath, $"uk.{project}", $"{project}.web", $"nts.uk.{project}.web");

                var xml = new PersistenceXml(context.RootPath, pathToWeb);
                xml.CreateCloudEdition(dataSourcesCount);

                Util.Gradle("build", pathToWeb);

                xml.RestoreOriginalFile();
            }

            loader.RestoreOriginalFile();
        }
    }

}

