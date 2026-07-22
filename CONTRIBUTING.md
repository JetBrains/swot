# Contributing to Swot

If you did not contribute to this repository before, please read [the repository rules](https://github.com/JetBrains/swot/tree/master#swot).

#### Data Contributions

* All of the school data is stored in a simple filesystem data structure under `lib/domains`.
* The directories are nested to match domain parts, starting with the [TLD](http://en.wikipedia.org/wiki/Top-level_domain) (Top Level Domain), then [SLDs](http://en.wikipedia.org/wiki/Second-level_domain) (Second Level Domains) beneath them, etc.
* There's a text file named after the school's domain, nested below the appropriate directory, e.g. `strath.ac.uk` would be listed as `lib/domains/uk/ac/strath.txt`.
* The contents of that file are simply the school name, e.g. `University of Strathclyde`.
* The [working example for University of Strathclyde](https://github.com/jetbrains/swot/blob/master/lib/domains/uk/ac/strath.txt) :eyes:
* Here's what the data structure looks like:

```
[-] lib
    [-] domains
        [-] edu
            [+] harvard
            [+] stanford
        [-] uk
            [-] ac
                [+] strath
```

##### Adding a School

Let's say you want to add the [Stanford University](http://www.stanford.edu/). We'd simply create a file at `lib/domains/edu/stanford.txt` containing the text `Stanford University`. You can safely ignores subdomain like `www`, `cis`, whatever. You can see the actual file [here](https://github.com/jetbrains/swot/blob/master/lib/domains/edu/stanford.txt).

#### Updating a School Name

Change the file contents of the appropriate file.

#### Deleting a School Name

A simple `git rm` on the approriate file will do the trick.

#### Refer the stoplist
Please do not suggest to add domains from the stoplist or abuse list. They're banned for the reason. The list currently looks like follows
* si.edu - anyone could register a domain there
* america.edu - anyone could register a domain there
* californiacolleges.edu - anyone could register e-mail account there
* cet.edu - this one's best. It could forward e-mails to your gmail.com even without registration. Cool.
* australia.edu - anyone could register e-mail account there
* liberty.edu - prior abuse

We intentionally exclude alumni domains (like john.smith@alumni.stanford.edu) as well.
