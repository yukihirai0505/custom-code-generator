## Summary

This is database table code generator for PlayFramework Scala.

## Usage

Modify application.conf file to your settings.

`/src/main/resources/application.conf`

```
# Sample
db_setting {
  url="jdbc:postgresql://{host}:{port}/{db_name}"
  user="{username}"
  password="{password}"
  outputFolder="../your_output_folder/"
  pkg="{package name}"
  outputFileName="{output file name}"
}
```


https://github.com/yukihirai0505/custom-code-generator/blob/master/src/main/resources/application.conf