package org.api;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import org.api.apiService.ApiService;
import org.api.movieDto.MovieDto;
import org.api.movieDto.MovieResponse;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        new Janela();
    }
}

class Janela extends JFrame {

    private JPanel jPanel;
    private JTextField textField;
    private JButton searchButton; // Botão de busca
    private JLabel label;
    private JLabel titleLabel;
    private JTextArea descriptionTextArea; // JTextArea para a descrição
    private JLabel posterLabel; // JLabel para mostrar o pôster

    public Janela() {
        // Criando os componentes
        searchButton = new JButton("Search");
        textField = new JTextField(30);
        label = new JLabel("MOVIE DB SEARCH");

        // Inicializando os rótulos para título, descrição e poster
        titleLabel = new JLabel("");
        descriptionTextArea = new JTextArea(5, 40); 
        descriptionTextArea.setLineWrap(true); 
        descriptionTextArea.setWrapStyleWord(true); 
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setOpaque(false); 

        posterLabel = new JLabel();

        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\Familia Gomesz\\Desktop\\Projeto1\\src\\main\\resources\\RubikMonoOne-Regular.ttf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // Registrando a fonte
            ge.registerFont(customFont);
            label.setFont(customFont); 
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // Inicializando o painel
        jPanel = new JPanel();
        jPanel.setBackground(Color.BLACK);
        jPanel.setLayout(new GridBagLayout()); // Usando GridBagLayout para controlar o posicionamento

        // Configurando as restrições de posicionamento (GridBagConstraints)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 0); // Espaçamento entre os componentes
        gbc.anchor = GridBagConstraints.NORTHWEST; // Alinha ao topo à esquerda

        // Adicionando o JLabel ao painel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        jPanel.add(label, gbc);
        label.setForeground(Color.RED); // Cor do texto

        // Adicionando o JTextField ao painel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Ocupa uma coluna
        gbc.fill = GridBagConstraints.HORIZONTAL;
        textField.setMaximumSize(new Dimension(300, 30)); // Define tamanho máximo
        jPanel.add(textField, gbc);

        // Adicionando o JButton de busca ao painel
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE; // Não redimensionar o botão
        searchButton.setMaximumSize(new Dimension(100, 30)); // Define tamanho máximo
        jPanel.add(searchButton, gbc);

        // Adicionando os labels de título, descrição e poster ao painel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Ocupa duas colunas
        titleLabel.setForeground(Color.WHITE);
        jPanel.add(titleLabel, gbc);

        gbc.gridy = 3;
        descriptionTextArea.setForeground(Color.WHITE);
        jPanel.add(descriptionTextArea, gbc); // Adicionando a JTextArea no lugar do JLabel

        gbc.gridy = 4;
        posterLabel.setHorizontalAlignment(JLabel.CENTER); // Centraliza a imagem do pôster
        jPanel.add(posterLabel, gbc);

        // Adicionando o listener para o botão de busca
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String movieName = textField.getText();
                searchMovie(movieName);
            }
        });

        // Adicionando o painel ao JFrame
        this.add(jPanel, BorderLayout.CENTER);
        searchButton.setBackground(Color.GRAY);

        // Configurações da janela
        setTitle("Movie Search");
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void searchMovie(String movieName) {
        ApiService api = new ApiService();
        try {
            MovieResponse movieResponse = api.getMovie(movieName);
            displayMovieInfo(movieResponse);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            titleLabel.setText("Error fetching movie data.");
            descriptionTextArea.setText("");
            posterLabel.setIcon(null); 
        }
    }

    private void displayMovieInfo(MovieResponse movieResponse) {
        List<MovieDto> movies = movieResponse.getResults();

        if (movies.isEmpty()) {
            titleLabel.setText("Movie not found.");
            descriptionTextArea.setText("");
            posterLabel.setIcon(null); // Limpa o pôster
            return;
        }

        MovieDto movie = movies.get(0); // Pega o primeiro resultado

        titleLabel.setText("Title: " + movie.getTitle());
        descriptionTextArea.setText("Description: " + movie.getOverview());

        // Atualiza o pôster
        String posterPath = movie.getPoster_path();
        if (posterPath != null && !posterPath.isEmpty()) {
            ImageIcon poster = new ImageIcon("https://image.tmdb.org/t/p/w500" + posterPath);
            posterLabel.setIcon(poster);
            posterLabel.setText(""); // Limpa o texto do JLabel se houver imagem
        } else {
            posterLabel.setIcon(null); // Remove o pôster se não houver
        }

        // Atualiza a interface gráfica
        jPanel.revalidate(); // Atualiza o painel
        jPanel.repaint(); // Repaint do painel
    }
}
