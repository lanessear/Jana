% -----------------------------------------------------------------------
% pi2.cls
% Based on pi1-muster.tex and defs.tex used before 2003 for
% PI 2 in Bremen.
% Adapted by Thomas Röfer and Malte Kuhn (uni@monkz.de)
% Further adapted by Sebastian Offermann and Florian Pflüger

\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{pi2}
\LoadClass[11pt,a4paper,twoside]{article}
\usepackage[ngerman]{babel}
\usepackage{fancyhdr}
\usepackage{graphicx}
\usepackage[utf8]{inputenc}
\usepackage{listings}
\usepackage{url}
\usepackage{color}
\usepackage{tocloft}

% -----------------------------------------------------------------------
% Page layout

% enlarge page
\setlength{\textwidth}{16cm}
\setlength{\topmargin}{-1cm}
\setlength{\evensidemargin}{0cm}
\setlength{\oddsidemargin}{0cm}
\setlength{\textheight}{24cm}

% skip between paragraphs
\setlength{\parskip}{1ex}
% ... and no indentation at start of a new paragraph
\setlength{\parindent}{0ex}

% some colors similar to eclipse syntax highlighting
\definecolor{jred}{rgb}{0.6,0,0}
\definecolor{jgreen}{RGB}{63,127,95}
\definecolor{jpurple}{RGB}{127,0,85}
\definecolor{jblue}{RGB}{42,0,255}
\definecolor{jlightblue}{RGB}{63,95,191}
\definecolor{jgrey}{rgb}{0.46,0.45,0.48}

\newcommand{\thissemester}{SoSe 2017}
\newcommand{\thistitle}{Praktische Informatik II}
\newcommand{\thisshorttitle}{PI 2} % used in headings

\pagestyle{fancy}
\thispagestyle{empty}

%Layout for lstlistings
\lstloadlanguages{Java} % Java-Sprache laden, notwendig wegen option 'savemem'
\lstset{
  language=Java,
  numbers=left,
  numberstyle=\tiny,
  numbersep=5pt,
  literate=%
    {Ö}{{\"O}}1
    {Ä}{{\"A}}1
    {Ü}{{\"U}}1
    {ß}{{\ss}}1
    {ü}{{\"u}}1
    {ä}{{\"a}}1
    {ö}{{\"o}}1
    {°}{{$^\circ$}}1,
  basicstyle=\ttfamily\scriptsize,
  showspaces=false,
  showtabs=false,
  showstringspaces=false,
  keywordstyle=\bfseries\color{jpurple},
  tabsize=2,
  frameround=ffff,
  extendedchars=true,
  commentstyle=\color{jgreen}\ttfamily,
  stringstyle=\color{jblue}\ttfamily,
  morecomment=[s][\color{jlightblue}]{/**}{*/},
  postbreak=\space,
  breakindent=5pt,
  breaklines=true
}

% -----------------------------------------------------------------------
% Main macros

\renewcommand{\maketitle}[7]
          {\@maketitle{#1}{#2}{#3}{#4}{#5}{#6}{#7}{Übungsblatt}}
\renewcommand\thesection{Aufgabe \arabic{section}}
\newcommand\skipsection{\stepcounter{section}}

\newlength{\seclen}
\settowidth{\seclen}{\textbf{Aufgabe }}
\newlength{\subseclen}
\settowidth{\subseclen}{Aufgabe }
\addtolength{\cftsecnumwidth}{\seclen}
\addtolength{\cftsubsecnumwidth}{\subseclen}
\addtolength{\cftsubsubsecnumwidth}{\subseclen}

% -----------------------------------------------------------------------
% Misc macros

\newcommand{\grenzop}[3]{\mathop{#1}\limits^{#2}_{#3}}
\newcommand{\langrechtspfeil}[2]{\grenzop{\longrightarrow}{#1}{#2}}
\newcommand{\bob}[0]{\hspace*{\fill}$\Box$}

% -----------------------------------------------------------------------
\setlength{\headheight}{13.6pt}

\renewcommand{\@maketitle}[8]
{
    \begin{tabular}[t]{lr}
      \begin{tabular}[t]{l}
        \begin{tabular}[t]{l@{\hskip 10mm}l}
            \thistitle & \thissemester \hspace{4ex} \\
            TutorIn: {#3}  &  Gruppe: {#4} \\
        \end{tabular} \vspace{5mm} \\
        {\Huge\sf {#8} {#1} } \\[1.5ex]
        {\Large Aufgabenl\"osung} \\[1ex]
        {\Large Abgabe: {#2}} \\[1ex]
      \end{tabular}
      &
      \begin{tabular}[t]{l}
          {#5}\\
          {#6}\\
          {#7}\\
      \end{tabular} \\[2ex]
    \end{tabular}\\
    \rule{\linewidth}{1pt}

% -----------------------------------------------------------------------
% Create headings (Thanks to Elmar Loos)

    \fancyhead{} %clear all fields
    \fancyhead[LO,RE]{\thisshorttitle, \thissemester}
    \fancyhead[RO,LE]{TutorIn: {#3}, Gruppe: {#4}, {#5} et al.}
    \fancyfoot{} %clear all fields
    \fancyfoot[RO,LE]{\thepage}
}

\endinput
