using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Build4Cloud
{
    class EntityManagerLoader
    {
        private readonly string path;

        public EntityManagerLoader(string rootPath)
        {
            path = Path.Combine(rootPath, @"uk.com\shr\nts.uk.shr.com\src\main\java\nts\uk\shr\infra\data\CloudEntityManagerLoader.java");
        }

        public void CreateCloudEdition(int datasourcesCount)
        {
            StashOriginalFile();

            var lines = File.ReadAllLines(PathToStashed);

            using (var writer = File.CreateText(path))
            {
                bool appended = false;

                foreach (string line in lines)
                {
                    writer.WriteLine(line);

                    if (appended)
                    {
                        continue;
                    }

                    if (line.Contains("private EntityManager entityManager;"))
                    {
                        AppendDataSources(writer, datasourcesCount);
                        appended = true;
                    }
                }
            }
        }

        private void AppendDataSources(StreamWriter writer, int datasourcesCount)
        {
            for (int i = 1; i <= datasourcesCount; i++)
            {
                writer.WriteLine($"@PersistenceContext(unitName = \"UK{i}\")");
                writer.WriteLine($"private EntityManager entityManager{i};");
            }
        }

        private void StashOriginalFile()
        {
            File.Move(path, PathToStashed);
        }

        public void RestoreOriginalFile()
        {
            File.Delete(path);
            File.Move(PathToStashed, path);
        }

        private String PathToStashed
        {
            get { return path + ".orig"; }
        }
    }
}
